package com.quangho.designpattern.saga.demo.payment.repository;

import com.quangho.designpattern.saga.demo.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Payment findById(int id);
}
