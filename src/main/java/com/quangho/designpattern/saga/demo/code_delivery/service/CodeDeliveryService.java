package com.quangho.designpattern.saga.demo.code_delivery.service;

import com.quangho.designpattern.saga.demo.code_delivery.model.CodeDelivery;
import com.quangho.designpattern.saga.demo.code_delivery.repository.CodeDeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeDeliveryService {
    @Autowired
    private CodeDeliveryRepository codeDeliveryRepository;

    public boolean deliverCode(String phoneNumber, String voucherCode) {
        // Make a request to 3rd party voucher code generation service to get a voucher code and return the received value
        return true;
    }

    public CodeDelivery createCodeDelivery(CodeDelivery codeDelivery) {
        return codeDeliveryRepository.save(codeDelivery);
    }
}
