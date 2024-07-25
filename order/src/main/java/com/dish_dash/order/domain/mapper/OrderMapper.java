package com.dish_dash.order.domain.mapper;

import com.dishDash.common.dto.*;
import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.model.OrderItem;
import com.dish_dash.order.domain.model.Rate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderMapper {
  OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

  OrderDto orderToDto(Order order);

  Order orderDtoToOrder(OrderDto orderDto);

  OrderItem orderItemDtoToOrderItem(OrderItemDto orderItemDto);
  OrderItem orderItemCreateDtoToOrderItem(OrderItemCreateDto orderItemDto);

  OrderItem orderItemCreatenDtoToOrderItem(OrderItemCreateDto orderItemDto);

  RateDto rateToRateDto(Rate rate);

  default double calculateTotalPrice(List<OrderItem> orderItems) {
    return orderItems.stream()
            .mapToDouble(orderItem -> orderItem.getPrice().getAmount())
            .sum();
  }
}
