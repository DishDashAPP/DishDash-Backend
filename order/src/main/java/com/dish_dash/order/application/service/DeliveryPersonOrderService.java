package com.dish_dash.order.application.service;

import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.model.OrderStatus;
import com.dish_dash.order.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPersonOrderService {

    @Autowired
    private OrderRepository orderRepository;

    public boolean updateOrderStatusByDeliveryPerson(String orderID, OrderStatus status) {
        Order order = orderRepository.findByID(orderID);
        if (order != null) {
            order.setStatus(status);
            orderRepository.save(order);
            return true;
        }
        return false;
    }

    public Order getDeliveryPersonCurrentOrder(String deliveryPersonID) {
        return orderRepository.findCurrentOrderByDeliveryPersonID(deliveryPersonID);
    }
}
