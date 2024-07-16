package com.dish_dash.order.application.service;

import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.model.OrderStatus;
import com.dish_dash.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;

  public Order viewOrder(Long orderID) {
    return orderRepository.findById(orderID).orElse(null);
  }

  public OrderStatus getOrderStatus(Long orderID) {
    Order order = orderRepository.findById(orderID).orElse(null);
    if (order != null) {
      return order.getStatus();
    }
    return null;
  }

  public boolean prepareOrder(Order order) {
    // TODO call updateStatus
    orderRepository.save(order);
    return true;
  }
}
