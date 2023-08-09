package com.quangho.designpattern.saga.demo.code_gen.service;

import com.quangho.designpattern.saga.demo.code_gen.model.CodeGen;
import com.quangho.designpattern.saga.demo.messaging_queue.CodeGenChannelReqToGenerateMsg;
import com.quangho.designpattern.saga.demo.messaging_queue.OrderChannelGeneratedCodeMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
public class CodeGenChannelHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    @Qualifier("order_queue")
    private Queue order_queue;

    @Autowired
    private CodeGenService codeGenService;

    @JmsListener(destination = "local.inmemory.code_gen_queue")
    public void onMessage(CodeGenChannelReqToGenerateMsg msg) {
        logger.info("CODE GEN SERVICE - Received request to generate code for order with id: " + msg.getOrderId());

        // Invoke 3rd party service to get voucher code
        String code = codeGenService.getVoucherCode(msg.getPhoneNumber());
        logger.info("CODE GEN SERVICE - Generated code: " + code + " for order with id: " + msg.getOrderId());

        // Save voucher code generation data to CODE GEN SERVICE
        CodeGen codeGen = new CodeGen();
        codeGen.setOrderId(msg.getOrderId());
        codeGen.setPhoneNumber(msg.getPhoneNumber());
        codeGen.setCode(code);
        codeGenService.saveVoucherCode(codeGen);

        // Notify order service about voucher code
        jmsTemplate.convertAndSend(order_queue, new OrderChannelGeneratedCodeMsg(codeGen.getOrderId(), codeGen.getPhoneNumber(), codeGen.getCode()));
        logger.info("CODE GEN SERVICE - Sent generated code to order service to deliver voucher code for order with id: " + codeGen.getOrderId());
    }
}
