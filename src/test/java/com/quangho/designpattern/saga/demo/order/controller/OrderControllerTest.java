package com.quangho.designpattern.saga.demo.order.controller;

import com.quangho.designpattern.saga.demo.order.helper.OrderStatus;
import com.quangho.designpattern.saga.demo.order.model.Order;
import com.quangho.designpattern.saga.demo.order.service.OrderService;
import org.assertj.core.api.Assertions;
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
class OrderControllerTest {
    @InjectMocks
    OrderController orderController;

    @Mock
    OrderService orderService;

    @Test
    void testGetOrders() {
        Order order1 = new Order(1, "0912345678", OrderStatus.PAID);
        Order order2 = new Order(2, "0123456789", OrderStatus.PAID);
        List orders = new ArrayList();
        orders.add(order1);
        orders.add(order2);
        when(orderService.getOrders()).thenReturn(orders);

        ResponseEntity<List<Order>> responseEntity = orderController.getOrders();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().size()).isEqualTo(2);
        assertThat(responseEntity.getBody().get(0).getDataPackageId()).isEqualTo(1);
        assertThat(responseEntity.getBody().get(1).getDataPackageId()).isEqualTo(2);
    }

    @Test
    void testCreateOrder() {
        Order order = new Order(1, "0912345678", OrderStatus.PAID);
        when(orderService.createOrder(any(Order.class))).thenReturn(order);

        ResponseEntity<Order> responseEntity = orderController.createOrder(order);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().getDataPackageId()).isEqualTo(1);
        assertThat(responseEntity.getBody().getPhoneNumber()).isEqualTo("0912345678");
        Assertions.assertThat(responseEntity.getBody().getOrderStatus()).isEqualTo(OrderStatus.PAID);
    }

    @Test
    void testGetOrderById() {
        Order order = new Order(1, "0912345678", OrderStatus.PAID);
        when(orderService.getOrderById(any(Integer.class))).thenReturn(order);

        ResponseEntity<Order> responseEntity = orderController.getOrderById(0);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().getId()).isEqualTo(0);
    }
}