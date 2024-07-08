package com.dish_dash.order.application.service;

import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.model.OrderStatus;
import com.dish_dash.order.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order viewOrder(String orderID) {
        return orderRepository.findById(orderID).orElse(null);
    }

    public OrderStatus getOrderStatus(String orderID) {
        Order order = orderRepository.findById(orderID).orElse(null);
        if (order != null) {
            return order.getStatus();
        }
        return null;
    }

    public boolean prepareOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        return savedOrder != null;
    }
}
