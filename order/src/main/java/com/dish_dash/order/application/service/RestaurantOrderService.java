package com.dish_dash.order.application.service;

import com.dishDash.common.dto.OrderDto;
import com.dishDash.common.enums.OrderStatus;
import com.dish_dash.order.domain.mapper.OrderMapper;
import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.repository.OrderRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

  public List<OrderDto> getRestaurantOwnerOrderHistory(Long restaurantOwnerID) {
    return orderRepository
        .findAllByRestaurantOwnerIdAndStatusIn(
            restaurantOwnerID,
            List.of(
                OrderStatus.DELIVERED,
                OrderStatus.NOT_PAID,
                OrderStatus.DELIVERING,
                OrderStatus.PREPARING))
        .stream()
        .map(OrderMapper.INSTANCE::orderToDto)
        .collect(Collectors.toList());
  }

  public List<OrderDto> getRestaurantOwnerActiveOrders(Long restaurantOwnerID) {
    return orderRepository
        .findAllByRestaurantOwnerIdAndStatusIn(
            restaurantOwnerID, List.of(OrderStatus.PREPARING, OrderStatus.DELIVERING))
        .stream()
        .map(OrderMapper.INSTANCE::orderToDto)
        .collect(Collectors.toList());
  }
}
