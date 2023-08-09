package com.quangho.designpattern.saga.demo.messaging_queue;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class CodeGenChannelReqToGenerateMsg implements Serializable {
    private static final long serialVersionUID = 1L;
    private int orderId;
    private String phoneNumber;
}
