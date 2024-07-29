package com.dish_dash.order.application.service;

import com.dishDash.common.dto.OrderDto;
import com.dishDash.common.enums.OrderStatus;
import com.dish_dash.order.domain.mapper.OrderMapper;
import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.repository.OrderRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryPersonOrderService {

  private final OrderRepository orderRepository;

  public boolean updateOrderStatusByDeliveryPerson(long orderID, OrderStatus status) {
    Optional<Order> order = orderRepository.findById(orderID);
    if (order.isPresent()) {
      order.get().setStatus(status);
      orderRepository.save(order.get());
      return true;
    }
    return false;
  }

  public OrderDto getDeliveryPersonCurrentOrder(long deliveryPersonID) {
    return OrderMapper.INSTANCE.orderToDto(orderRepository.findByDeliveryPersonId(deliveryPersonID));
  }
}
