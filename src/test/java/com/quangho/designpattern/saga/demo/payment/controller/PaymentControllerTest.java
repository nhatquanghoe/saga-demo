package com.quangho.designpattern.saga.demo.payment.controller;

import com.quangho.designpattern.saga.demo.payment.helper.PaymentStatus;
import com.quangho.designpattern.saga.demo.payment.model.Payment;
import com.quangho.designpattern.saga.demo.payment.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
class PaymentControllerTest {
    @InjectMocks
    PaymentController paymentController;

    @Mock
    PaymentService paymentService;

    @Test
    void testGetPayments() {
        Payment payment1 = new Payment(1, 49.99f, PaymentStatus.PAID);
        Payment payment2 = new Payment(2, 99.99f, PaymentStatus.PAID);
        List payments = new ArrayList();
        payments.add(payment1);
        payments.add(payment2);
        when(paymentService.getPayments()).thenReturn(payments);

        ResponseEntity<List<Payment>> responseEntity = paymentController.getPayments();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().size()).isEqualTo(2);
        assertThat(responseEntity.getBody().get(0).getOrderId()).isEqualTo(1);
        assertThat(responseEntity.getBody().get(1).getOrderId()).isEqualTo(2);
    }

    @Test
    void testCreatePayment() {
        Payment payment = new Payment(1, 49.99f, PaymentStatus.PAID);
        when(paymentService.createPayment(any(Payment.class))).thenReturn(payment);

        ResponseEntity<Payment> responseEntity = paymentController.createPayment(payment);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().getOrderId()).isEqualTo(1);
        assertThat(responseEntity.getBody().getPaymentStatus()).isEqualTo(PaymentStatus.PAID);
    }

    @Test
    void testGetPaymentById() {
        Payment payment = new Payment(1, 49.99f, PaymentStatus.PAID);
        when(paymentService.getPaymentById(any(Integer.class))).thenReturn(payment);

        ResponseEntity<Payment> responseEntity = paymentController.getPaymentById(0);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().getId()).isEqualTo(0);
    }
}