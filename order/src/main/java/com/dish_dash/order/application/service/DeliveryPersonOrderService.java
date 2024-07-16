package com.dish_dash.order.application.service;

import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.model.OrderStatus;
import com.dish_dash.order.domain.repository.OrderRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryPersonOrderService {

  private final OrderRepository orderRepository;

  public boolean updateOrderStatusByDeliveryPerson(Long orderID, OrderStatus status) {
    Optional<Order> order = orderRepository.findById(orderID);
    if (order.isPresent()) {
      order.get().setStatus(status);
      orderRepository.save(order.get());
      return true;
    }
    return false;
  }

  public Order getDeliveryPersonCurrentOrder(String deliveryPersonID) {
    //    todo This is wrong
    //    return orderRepository.findCurrentOrderByDeliveryPersonID(deliveryPersonID);
    return Order.builder().build();
  }
}
