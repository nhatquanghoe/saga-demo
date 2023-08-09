package com.quangho.designpattern.saga.demo.order.controller;

import com.quangho.designpattern.saga.demo.order.model.Order;
import com.quangho.designpattern.saga.demo.order.service.OrderService;
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
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders() {
        logger.info("Received request to find all orders");
        return new ResponseEntity<>(orderService.getOrders(), HttpStatus.OK);
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        logger.info("Received request to create a new order");
        return ResponseEntity.status(HttpStatus.OK).body(orderService.createOrder(order));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") int orderId) {
        logger.info("Received request to find order by Id: {}", orderId);
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderById(orderId));
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") int orderId, @Valid @RequestBody Order order) {
        logger.info("Received request to update order by Id: {}", orderId);
        return ResponseEntity.status(HttpStatus.OK).body(orderService.updateOrder(orderId, order));
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable("id") int orderId) {
        logger.info("Received request to delete order by Id: {}", orderId);
        return ResponseEntity.status(HttpStatus.OK).body(orderService.deleteOrder(orderId));
    }
}
