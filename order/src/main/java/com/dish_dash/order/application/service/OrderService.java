package com.dish_dash.order.application.service;

import com.dishDash.common.dto.OrderDto;
import com.dishDash.common.enums.OrderStatus;
import com.dish_dash.order.domain.mapper.OrderMapper;
import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.repository.OrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;

  public OrderDto viewOrder(long orderID) {
    return orderRepository.findById(orderID).map(OrderMapper.INSTANCE::orderToDto).orElse(null);
  }

  public OrderStatus getOrderStatus(long orderID) {
    Order order = orderRepository.findById(orderID).orElse(null);
    if (order != null) {
      return order.getStatus();
    }
    return null;
  }

  public List<Order> getAllOrderByRestaurantOwnerId(long restaurantId) {
    return orderRepository.findAllByRestaurantOwnerId(restaurantId);
  }

  public boolean prepareOrder(OrderDto orderDto) {
    orderRepository.save(OrderMapper.INSTANCE.orderDtoToOrder(orderDto));
    return true;
  }
}
