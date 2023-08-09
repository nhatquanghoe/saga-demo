package com.quangho.designpattern.saga.demo.code_gen.service;

import com.quangho.designpattern.saga.demo.code_gen.model.CodeGen;
import com.quangho.designpattern.saga.demo.code_gen.repository.CodeGenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeGenService {
    @Autowired
    private CodeGenRepository codeGenRepository;

    public String getVoucherCode(String phoneNumber) {
        // Make a request to 3rd party voucher code generation service to get a voucher code
        // Here we use a internal function to generate a random code
        return getRandomString(14);
    }

    public CodeGen saveVoucherCode(CodeGen codeGen) {
        return codeGenRepository.save(codeGen);
    }

    private String getRandomString(int i) {
        String theAlphaNumericS;
        StringBuilder builder;

        theAlphaNumericS = "0123456789";

        //create the StringBuffer
        builder = new StringBuilder(i);

        for (int m = 0; m < i; m++) {

            // generate numeric
            int myindex
                    = (int) (theAlphaNumericS.length()
                    * Math.random());

            // add the characters
            builder.append(theAlphaNumericS
                    .charAt(myindex));
        }

        return builder.toString();
    }
}
