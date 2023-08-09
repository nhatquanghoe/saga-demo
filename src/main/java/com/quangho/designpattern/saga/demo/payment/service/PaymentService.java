package com.quangho.designpattern.saga.demo.payment.service;

import com.quangho.designpattern.saga.demo.messaging_queue.OrderChannelPaidPaymentMsg;
import com.quangho.designpattern.saga.demo.order.helper.OrderStatus;
import com.quangho.designpattern.saga.demo.payment.helper.PaymentStatus;
import com.quangho.designpattern.saga.demo.payment.model.Payment;
import com.quangho.designpattern.saga.demo.payment.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Queue;
import java.util.List;

@Service
public class PaymentService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    @Qualifier("order_queue")
    private Queue order_queue;

    public Payment createPayment(Payment payment) {
        try {
            payment.setPaymentStatus(PaymentStatus.PAID);
            Payment savedPayment = paymentRepository.save(payment);
            logger.info("Created payment for order with id: " + savedPayment.getOrderId());
            // Publish a message about new payment
            publishOrderChannelPaidPaymentMsg(payment.getOrderId());

            return payment;
        } catch (Exception e) {
            throw new RuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
    }

    public Payment getPaymentById(int paymentId) {
        Payment payment = paymentRepository.findById(paymentId);

        if (payment == null) {
            throw new RuntimeException(HttpStatus.NOT_FOUND.toString());
        } else {
            logger.info("Found payment with id: " + paymentId);
            return payment;
        }
    }

    public List<Payment> getPayments() {
        List<Payment> payments = paymentRepository.findAll();
        logger.info("Number of payments found: " + payments.size());
        return payments;
    }

    public Payment updatePayment(int paymentId, Payment payment) {
        try {
            Payment paymentExist = paymentRepository.findById(paymentId);

            if (paymentExist == null) {
                throw new RuntimeException(HttpStatus.NOT_FOUND.toString());
            }

            paymentExist = paymentRepository.save(payment);
            logger.info("Updated payment with id: " + paymentId);
            return paymentExist;
        } catch (Exception e) {
            throw new RuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
    }

    public Payment deletePayment(int paymentId) {
        try {
            Payment paymentExist = paymentRepository.findById(paymentId);

            if (paymentExist == null) {
                throw new RuntimeException(HttpStatus.NOT_FOUND.toString());
            }

            paymentRepository.deleteById(paymentId);
            logger.info("Deleted payment with id: " + paymentId);
            return paymentExist;
        } catch (Exception e) {
            throw new RuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
    }

    public void publishOrderChannelPaidPaymentMsg(int orderId) {
        jmsTemplate.convertAndSend(order_queue, new OrderChannelPaidPaymentMsg(orderId, OrderStatus.PAID));
        logger.info("PAYMENT SERVICE - Sent a message to notify order service about payment for order with id: " + orderId);
    }
}
