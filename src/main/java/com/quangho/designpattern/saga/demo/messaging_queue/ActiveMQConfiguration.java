package com.quangho.designpattern.saga.demo.messaging_queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.Queue;

@EnableJms
@Configuration
public class ActiveMQConfiguration {
    @Bean("order_queue")
    public Queue createOrderQueue() {
        return new ActiveMQQueue("local.inmemory.order_queue");
    }

    @Bean("code_gen_queue")
    public Queue createVoucherCodeQueue() {
        return new ActiveMQQueue("local.inmemory.code_gen_queue");
    }

    @Bean("code_delivery_queue")
    public Queue createCodeDeliveryQueue() {
        return new ActiveMQQueue("local.inmemory.code_delivery_queue");
    }
}
