package com.quangho.designpattern.saga.demo.messaging_queue;

import com.quangho.designpattern.saga.demo.order.helper.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class OrderChannelPaidPaymentMsg implements Serializable {
    private static final long serialVersionUID = 1L;
    private int orderId;
    private OrderStatus orderStatus;
}
