package com.dish_dash.order.application.service;

import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.model.OrderStatus;
import com.dish_dash.order.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantOrderService {

    @Autowired
    private OrderRepository orderRepository;

    public boolean updateOrderStatusByRestaurantOwner(String orderID, OrderStatus status) {
        Order order = orderRepository.findByID(orderID);
        if (order != null) {
            order.setStatus(status);
            orderRepository.save(order);
            return true;
        }
        return false;
    }

    public List<Order> getRestaurantOwnerOrderHistory(String restaurantOwnerID) {
        return orderRepository.findOrderHistoryByRestaurantOwnerID(restaurantOwnerID);
    }

    public List<Order> getRestaurantOwnerActiveOrders(String restaurantOwnerID) {
        return orderRepository.findActiveOrdersByRestaurantOwnerID(restaurantOwnerID);
    }
}
