package com.quangho.designpattern.saga.demo.messaging_queue;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class OrderChannelDeliveredCodeMsg implements Serializable {
    private static final long serialVersionUID = 1L;
    protected int orderId;
    protected String phoneNumber;
    protected String code;
}
