package com.dish_dash.order.application.service;

import com.dishDash.common.dto.OrderDto;
import com.dishDash.common.enums.ErrorCode;
import com.dishDash.common.enums.OrderStatus;
import com.dishDash.common.exception.CustomException;
import com.dishDash.common.feign.user.UserApi;
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
  private final UserApi userApi;

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
    Optional<Order> order = orderRepository.findByDeliveryPersonId(deliveryPersonID);
    if (order.isPresent()) {
      OrderDto orderDto = OrderMapper.INSTANCE.orderToDto(order.get());
      orderDto.setDeliveryPersonDto(userApi.getDeliveryPersonProfile(deliveryPersonID));
      orderDto.setCustomerDto(userApi.getCustomerProfile(orderDto.getCustomerId()));
      orderDto.setRestaurantOwnerDto(
          userApi.getRestaurantOwnerProfile(orderDto.getRestaurantOwnerId()));
    }
    throw new CustomException(ErrorCode.BAD_REQUEST, "order not found");
  }
}
