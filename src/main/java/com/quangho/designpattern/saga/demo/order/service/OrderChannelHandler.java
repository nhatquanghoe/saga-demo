package com.quangho.designpattern.saga.demo.order.service;

import com.quangho.designpattern.saga.demo.messaging_queue.CodeDeliveryChannelReqToDeliverMsg;
import com.quangho.designpattern.saga.demo.messaging_queue.CodeGenChannelReqToGenerateMsg;
import com.quangho.designpattern.saga.demo.messaging_queue.OrderChannelDeliveredCodeMsg;
import com.quangho.designpattern.saga.demo.messaging_queue.OrderChannelGeneratedCodeMsg;
import com.quangho.designpattern.saga.demo.messaging_queue.OrderChannelPaidPaymentMsg;
import com.quangho.designpattern.saga.demo.order.helper.OrderStatus;
import com.quangho.designpattern.saga.demo.order.model.Order;
import javassist.NotFoundException;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Queue;

@Component
public class OrderChannelHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    @Qualifier("code_gen_queue")
    private Queue code_gen_queue;

    @Autowired
    @Qualifier("code_delivery_queue")
    private Queue code_delivery_queue;

    @Autowired
    private OrderService orderService;

    @JmsListener(destination = "local.inmemory.order_queue")
    public void onMessage(ActiveMQObjectMessage msg) throws JMSException {
        Object msgObject = msg.getObject();
        if (msgObject instanceof OrderChannelPaidPaymentMsg) {
            onMessage((OrderChannelPaidPaymentMsg) msgObject);
            return;
        }

        if (msgObject instanceof OrderChannelGeneratedCodeMsg) {
            onMessage((OrderChannelGeneratedCodeMsg) msgObject);
            return;
        }

        if (msgObject instanceof OrderChannelDeliveredCodeMsg) {
            onMessage((OrderChannelDeliveredCodeMsg) msgObject);
            return;
        }
    }

    public void onMessage(OrderChannelPaidPaymentMsg msg) {
        logger.info("ORDER SERVICE - Received payment information for order with id: " + msg.getOrderId());

        try {
            orderService.updateOrderStatus(msg.getOrderId(), msg.getOrderStatus());
            logger.info("ORDER SERVICE - Updated order status as " + msg.getOrderStatus() + " for order with id: " + msg.getOrderId());
            Order order = orderService.getOrderById(msg.getOrderId());
            jmsTemplate.convertAndSend(code_gen_queue, new CodeGenChannelReqToGenerateMsg(order.getId(), order.getPhoneNumber()));
            logger.info("ORDER SERVICE - Sent request to generate code for order with id: " + msg.getOrderId());
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
        }
    }

    public void onMessage(OrderChannelGeneratedCodeMsg msg) {
        logger.info("ORDER SERVICE - Received generated voucher code information for order with id: " + msg.getOrderId());

        try {
            // Update order status
            orderService.updateOrderStatus(msg.getOrderId(), OrderStatus.GENARATED_CODE);
            logger.info("ORDER SERVICE - Updated order status as " + OrderStatus.GENARATED_CODE + "for order with id: " + msg.getOrderId());

            // Send a request to CODE DELIVERY SERVICE to deliver voucher code
            jmsTemplate.convertAndSend(code_delivery_queue, new CodeDeliveryChannelReqToDeliverMsg(msg.getOrderId(), msg.getCode(), msg.getPhoneNumber()));
            logger.info("ORDER SERVICE - Sent request to deliver voucher code for order with id: " + msg.getOrderId());
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
        }
    }

    public void onMessage(OrderChannelDeliveredCodeMsg msg) {
        logger.info("ORDER SERVICE - Received code delivery information for order with id: " + msg.getOrderId());

        try {
            // Update order status
            orderService.updateOrderStatus(msg.getOrderId(), OrderStatus.COMPLETED);
            logger.info("ORDER SERVICE - Completed order with id: " + msg.getOrderId());
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
        }
    }
}
