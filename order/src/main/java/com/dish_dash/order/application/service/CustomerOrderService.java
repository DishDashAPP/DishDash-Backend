package com.dish_dash.order.application.service;

import com.dishDash.common.dto.OrderDto;
import com.dishDash.common.dto.OrderItemDto;
import com.dish_dash.order.domain.mapper.OrderMapper;
import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.model.Rate;
import com.dish_dash.order.domain.repository.OrderRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerOrderService {

  private final OrderRepository orderRepository;

  public OrderDto createOrder(
      Long customerId, Long restaurantOwnerId, List<OrderItemDto> orderItemsDto) {
    Order order =
        Order.builder()
            .customerId(customerId)
            .restaurantOwnerId(restaurantOwnerId)
            .orderItems(
                orderItemsDto.stream()
                    .map(OrderMapper.INSTANCE::orderItemDtoToOrderItem)
                    .collect(Collectors.toList()))
            .build();
    return OrderMapper.INSTANCE.orderToDto(orderRepository.save(order));
  }

  public OrderDto modifyOrder(Long orderId, List<OrderItemDto> orderItemsDto) {
    Optional<Order> orderOptional = orderRepository.findById(orderId);
    if (orderOptional.isPresent()) {
      orderOptional
          .get()
          .setOrderItems(
              orderItemsDto.stream()
                  .map(OrderMapper.INSTANCE::orderItemDtoToOrderItem)
                  .collect(Collectors.toList()));
      return OrderMapper.INSTANCE.orderToDto(orderRepository.save(orderOptional.get()));
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

  public List<OrderDto> getCustomerOrders(Long customerID) {
    return orderRepository.findByCustomerId(customerID).stream()
        .map(OrderMapper.INSTANCE::orderToDto)
        .collect(Collectors.toList());
  }

  public OrderDto getCustomerCurrentOrder(Long customerID) {
    //    TODO GET FROM USER MODULE
    //    return orderRepository.findCurrentOrderByCustomerId(customerID);
    return OrderMapper.INSTANCE.orderToDto(Order.builder().build());
  }
}
