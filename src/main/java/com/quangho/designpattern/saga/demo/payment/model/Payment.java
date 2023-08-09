package com.quangho.designpattern.saga.demo.payment.model;

import com.quangho.designpattern.saga.demo.payment.helper.PaymentStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity(name = "payment")
@Table(name = "payment")
@Data
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected int id;

    @Column(name = "order_id")
    protected int orderId;

    @Column(name = "payment_amount")
    protected float paymentAmount;

    @Column(name = "payment_status")
    protected PaymentStatus paymentStatus;

    @CreationTimestamp
    private Timestamp created_at;

    @UpdateTimestamp
    private Timestamp updated_at;

    public Payment(int orderId, float paymentAmount, PaymentStatus paymentStatus) {
        this.orderId = orderId;
        this.paymentAmount = paymentAmount;
        this.paymentStatus = paymentStatus;
    }
}
