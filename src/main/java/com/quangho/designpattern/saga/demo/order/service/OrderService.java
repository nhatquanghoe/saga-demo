package com.quangho.designpattern.saga.demo.order.service;

import com.quangho.designpattern.saga.demo.order.helper.OrderStatus;
import com.quangho.designpattern.saga.demo.order.model.Order;
import com.quangho.designpattern.saga.demo.order.repository.OrderRepository;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Order order) {
        try {
            order.setOrderStatus(OrderStatus.OPEN);
            Order savedOrder = orderRepository.save(order);
            logger.info("Created order with id: " + savedOrder.getId());
            return order;
        } catch (Exception e) {
            throw e;
        }
    }

    public Order getOrderById(int orderId) {
        Order order = orderRepository.findById(orderId);

        if (order == null) {
            logger.info("Found order with id: " + orderId);
            throw new RuntimeException(HttpStatus.NOT_FOUND.toString());
        } else {
            return order;
        }
    }

    public List<Order> getOrders() {
        List<Order> orders = orderRepository.findAll();
        logger.info("Number of orders found: " + orders.size());
        return orders;
    }

    public Order updateOrder(int orderId, Order order) {
        try {
            Order orderExist = orderRepository.findById(orderId);

            if (orderExist == null) {
                throw new RuntimeException(HttpStatus.NOT_FOUND.toString());
            }

            logger.info("Updated order with id: " + orderId);
            orderExist = orderRepository.save(order);
            return orderExist;
        } catch (Exception e) {
            throw new RuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
    }

    public Order deleteOrder(int orderId) {
        try {
            Order orderExist = orderRepository.findById(orderId);

            if (orderExist == null) {
                throw new RuntimeException(HttpStatus.NOT_FOUND.toString());
            }

            orderRepository.deleteById(orderId);
            logger.info("Deleted order with id: " + orderId);
            return orderExist;
        } catch (Exception e) {
            throw new RuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
    }

    public void updateOrderStatus(int orderId, OrderStatus orderStatus) throws NotFoundException {
        Order orderExist = orderRepository.findById(orderId);
        if (orderExist == null) {
            throw new NotFoundException("Failed to update order status: not found order with provided id " + orderId);
        } else {
            orderExist.setOrderStatus(orderStatus);
        }
    }
}
