package com.quangho.designpattern.saga.demo.code_delivery.service;

import com.quangho.designpattern.saga.demo.code_delivery.model.CodeDelivery;
import com.quangho.designpattern.saga.demo.messaging_queue.CodeDeliveryChannelReqToDeliverMsg;
import com.quangho.designpattern.saga.demo.messaging_queue.OrderChannelDeliveredCodeMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
public class CodeDeliveryChannelHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    @Qualifier("order_queue")
    private Queue order_queue;

    @Autowired
    @Qualifier("code_delivery_queue")
    private Queue code_delivery_queue;

    @Autowired
    private CodeDeliveryService codeDeliveryService;

    @JmsListener(destination = "local.inmemory.code_delivery_queue")
    public void onMessage(CodeDeliveryChannelReqToDeliverMsg msg) {
        logger.info("CODE DELIVERY SERVICE - Received request to deliver code for order with id: " + msg.getOrderId());

        // Invoke 3rd party service to get voucher code
        codeDeliveryService.deliverCode(msg.getPhoneNumber(), msg.getVoucherCode());
        logger.info("CODE DELIVERY SERVICE - Delivered code for order with id: " + msg.getOrderId());

        // Save delivery data to CODE DELIVERY SERVICE
        CodeDelivery codeDelivery = new CodeDelivery();
        codeDelivery.setOrderId(msg.getOrderId());
        codeDelivery.setPhoneNumber(msg.getPhoneNumber());
        codeDelivery.setCode(msg.getVoucherCode());
        codeDeliveryService.createCodeDelivery(codeDelivery);

        // Notify order service about voucher code delivery
        jmsTemplate.convertAndSend(order_queue, new OrderChannelDeliveredCodeMsg(codeDelivery.getOrderId(), codeDelivery.getPhoneNumber(), codeDelivery.getCode()));
        logger.info("CODE DELIVERY SERVICE - Sent delivery information to order service to completed order: " + codeDelivery.getOrderId());
    }
}
