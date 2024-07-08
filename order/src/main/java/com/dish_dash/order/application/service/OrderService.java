package com.dish_dash.order.application.service;

import com.dish_dash.order.domain.model.Order;

public interface OrderService {
    Order viewOrder(String orderID);
    OrderStatus getOrderStatus(String orderID);
    boolean prepareOrder(Order order);
}
