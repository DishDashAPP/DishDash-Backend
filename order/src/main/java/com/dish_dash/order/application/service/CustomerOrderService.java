package com.dish_dash.order.application.service;

import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.model.OrderItem;
import com.dish_dash.order.domain.model.Rate;
import com.dish_dash.order.domain.repository.OrderRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerOrderService {

  private final OrderRepository orderRepository;

  public Order createOrder(Long customerId, Long restaurantOwnerId, List<OrderItem> orderItems) {
    Order order =
        Order.builder()
            .customerId(customerId)
            .restaurantOwnerId(restaurantOwnerId)
            .orderItems(orderItems)
            .build();
    return orderRepository.save(order);
  }

  public Order modifyOrder(Long orderId, List<OrderItem> orderItems) {
    Optional<Order> orderOptional = orderRepository.findById(orderId);
    if (orderOptional.isPresent()) {
      orderOptional.get().setOrderItems(orderItems);
      return orderRepository.save(orderOptional.get());
    }
    return null;
  }

  public boolean setOrderRate(Long customerId, Long orderID, int point) {
    Optional<Order> orderOptional = orderRepository.findById(orderID);
    if (orderOptional.isPresent()) {
      orderRepository.save(orderOptional.get());
      return true;
    }
    return false;
  }

  public boolean setDeliveryRate(Long customerID, Long orderID, int point) {
    Optional<Order> order = orderRepository.findById(orderID);
    if (order.isPresent()) {
      order.get().setDeliveryPersonRate(Rate.builder().build());
      orderRepository.save(order.get());
      return true;
    }
    return false;
  }

  public List<Order> getCustomerOrders(Long customerID) {
    return orderRepository.findByCustomerId(customerID);
  }

  public Order getCustomerCurrentOrder(Long customerID) {
    //    TODO GET FROM USER MODULE
    //    return orderRepository.findCurrentOrderByCustomerId(customerID);
    return Order.builder().build();
  }
}
