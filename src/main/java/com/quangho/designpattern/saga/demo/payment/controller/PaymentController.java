package com.quangho.designpattern.saga.demo.payment.controller;

import com.quangho.designpattern.saga.demo.payment.model.Payment;
import com.quangho.designpattern.saga.demo.payment.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/payments")
    public ResponseEntity<List<Payment>> getPayments() {
        logger.info("Received request to find all payments");
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getPayments());
    }

    @PostMapping("/payments")
    public ResponseEntity<Payment> createPayment(@Valid @RequestBody Payment payment) {
        logger.info("Received request to create a new payment");
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.createPayment(payment));
    }

    @GetMapping("/payments/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable("id") int paymentId) {
        logger.info("Received request to find payment by Id: {}", paymentId);
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getPaymentById(paymentId));
    }

    @PutMapping("/payments/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable("id") int paymentId, @Valid @RequestBody Payment payment) {
        logger.info("Received request to update payment by Id: {}", paymentId);
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.updatePayment(paymentId, payment));
    }

    @DeleteMapping("/payments/{id}")
    public ResponseEntity<Payment> deletePayment(@PathVariable("id") int paymentId) {
        logger.info("Received request to delete payment by Id: {}", paymentId);
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.deletePayment(paymentId));
    }
}
