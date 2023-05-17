package com.example.coffe_shop.service;

import com.example.coffe_shop.model.entity.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    Order getOrderById(String id);
    List<Order> getAllOrders();
    void updateOrder(String id, Order updatedOrder);
    void deleteOrder(String id);
    double calculateOrderSubtotal(Order order);
    double calculateOrderTotal(Order order);
}

