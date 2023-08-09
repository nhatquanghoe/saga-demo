package com.quangho.designpattern.saga.demo.order.model;

import com.quangho.designpattern.saga.demo.order.helper.OrderStatus;
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

@Entity(name = "package_order")
@Table(name = "package_order")
@NoArgsConstructor
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected int id;

    @Column(name = "data_package_id")
    protected int dataPackageId;

    @Column(name = "phone_number")
    protected String phoneNumber;

    @Column(name = "order_status")
    protected OrderStatus orderStatus;

    @CreationTimestamp
    private Timestamp created_at;

    @UpdateTimestamp
    private Timestamp updated_at;

    public Order(int dataPackageId, String phoneNumber, OrderStatus orderStatus) {
        this.dataPackageId = dataPackageId;
        this.phoneNumber = phoneNumber;
        this.orderStatus = orderStatus;
    }
}
