package com.dish_dash.order.application.service;

import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.model.OrderItem;
import com.dish_dash.order.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerOrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Transaction createOrder(String customerID, String restaurantOwnerID, OrderItem[] orderItems) {
        // Implementation logic for creating an order
        Order newOrder = new Order();
        newOrder.setCustomerID(customerID);
        newOrder.setRestaurantOwnerID(restaurantOwnerID);
        newOrder.setOrderItems(orderItems);
        return orderRepository.createOrder(newOrder.getOrderID(), newOrder);
    }

    public Order modifyOrder(String orderID, OrderItem[] orderItems) {
        // Implementation logic for modifying an order
        return orderRepository.modifyOrder(orderID, orderItems);
    }

    public boolean setOrderRate(String customerID, String orderID, int point) {
        // Implementation logic for setting order rate
        Order order = orderRepository.findByID(orderID);
        if (order != null) {
            order.setCustomerRate(point);
            orderRepository.save(order);
            return true;
        }
        return false;
    }

    public boolean setDeliveryRate(String customerID, String orderID, int point) {
        // Implementation logic for setting delivery rate
        Order order = orderRepository.findByID(orderID);
        if (order != null) {
            order.setDeliveryPersonRate(point);
            orderRepository.save(order);
            return true;
        }
        return false;
    }

    public List<Order> getCustomerOrders(String customerID) {
        // Implementation logic for getting customer orders
        return orderRepository.findByCustomerID(customerID);
    }

    public Order getCustomerCurrentOrder(String customerID) {
        // Implementation logic for getting current order
        return orderRepository.findCurrentOrderByCustomerID(customerID);
    }
}
