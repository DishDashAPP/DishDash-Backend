package com.dish_dash.order.application.service;

import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.model.OrderStatus;
import com.dish_dash.order.domain.repository.OrderRepository;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order viewOrder(String orderID) {
        return orderRepository.findByID(orderID);
    }

    @Override
    public OrderStatus getOrderStatus(String orderID) {
        return orderRepository.findByID(orderID).getStatus();
    }

    @Override
    public boolean prepareOrder(Order order) {
        order.updateStatus(OrderStatus.PREPARING);
        return orderRepository.modifyOrder(order) != null;
    }
}
