package com.dish_dash.order.application.service;

import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.model.OrderStatus;
import com.dish_dash.order.domain.repository.OrderRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantOrderService {

  private final OrderRepository orderRepository;

  public boolean updateOrderStatusByRestaurantOwner(Long orderId, OrderStatus status) {
    Optional<Order> orderOptional = orderRepository.findById(orderId);
    if (orderOptional.isPresent()) {
      orderOptional.get().setStatus(status);
      orderRepository.updateStatus(status, orderId);
      return true;
    }
    return false;
  }

  public List<Order> getRestaurantOwnerOrderHistory(Long restaurantOwnerID) {
    return orderRepository.findAllByRestaurantOwnerIdAndStatusIn(
        restaurantOwnerID,
        List.of(
            OrderStatus.DELIVERED,
            OrderStatus.NOT_PAID,
            OrderStatus.DELIVERING,
            OrderStatus.PREPARING));
  }

  public List<Order> getRestaurantOwnerActiveOrders(Long restaurantOwnerID) {
    return orderRepository.findAllByRestaurantOwnerIdAndStatusIn(
        restaurantOwnerID, List.of(OrderStatus.PREPARING, OrderStatus.DELIVERING));
  }
}
