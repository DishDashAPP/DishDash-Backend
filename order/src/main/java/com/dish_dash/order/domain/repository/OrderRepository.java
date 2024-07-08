package com.dish_dash.order.domain.repository;

import com.dish_dash.order.domain.model.Order;

public interface OrderRepository {
    Order findByID(String id);
    Order createOrder(Order order);
    Order modifyOrder(Order order);
}
