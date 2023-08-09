package com.quangho.designpattern.saga.demo.order.repository;

import com.quangho.designpattern.saga.demo.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findById(int id);
}
