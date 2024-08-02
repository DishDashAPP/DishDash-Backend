package com.dish_dash.order.application.service;

import com.dishDash.common.dto.OrderDto;
import com.dishDash.common.enums.ErrorCode;
import com.dishDash.common.enums.OrderStatus;
import com.dishDash.common.exception.CustomException;
import com.dishDash.common.feign.user.UserApi;
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
  private final UserApi userApi;

  public boolean updateOrderStatusByRestaurantOwner(
      Long restaurantOwnerId, long orderId, OrderStatus status) {
    Optional<Order> orderOptional = orderRepository.findById(orderId);
    if (orderOptional.isPresent()) {
      if (orderOptional.get().getRestaurantOwnerId() != restaurantOwnerId)
        throw new CustomException(ErrorCode.FORBIDDEN, "Restaurant owner id mismatch");

      // we should set a delivery person to the order or throw an exception
      long setDeliveryPerson = userApi.setActiveOrder(orderId);
      if (setDeliveryPerson != 0) {
        orderOptional.get().setStatus(status);
        orderOptional.get().setDeliveryPersonId(setDeliveryPerson);
        orderRepository.updateDeliveryPerson(setDeliveryPerson, orderId);
        orderRepository.updateStatus(status, orderId);
        return true;
      } else throw new CustomException(ErrorCode.BAD_REQUEST, "No delivery person available");
    }
    throw new CustomException(ErrorCode.BAD_REQUEST, "No order available");
  }

  public List<OrderDto> getRestaurantOwnerOrderHistory(long restaurantOwnerID) {
    return orderRepository
        .findAllByRestaurantOwnerIdAndStatusIn(
            restaurantOwnerID,
            List.of(OrderStatus.DELIVERED, OrderStatus.NOT_PAID, OrderStatus.DELIVERING))
        .stream()
        .map(
            order -> {
              OrderDto orderDto = OrderMapper.INSTANCE.orderToDto(order);
              orderDto.setDeliveryPersonDto(
                  userApi.getDeliveryPersonProfile(order.getDeliveryPersonId()));
              orderDto.setCustomerDto(userApi.getCustomerProfile(order.getCustomerId()));
              return orderDto;
            })
        .collect(Collectors.toList());
  }

  public List<OrderDto> getRestaurantOwnerActiveOrders(long restaurantOwnerID) {
    return orderRepository
        .findAllByRestaurantOwnerIdAndStatusIn(
            restaurantOwnerID, List.of(OrderStatus.PREPARING))
        .stream()
        .map(
            order -> {
              OrderDto orderDto = OrderMapper.INSTANCE.orderToDto(order);
              orderDto.setDeliveryPersonDto(
                  userApi.getDeliveryPersonProfile(order.getDeliveryPersonId()));
              orderDto.setCustomerDto(userApi.getCustomerProfile(order.getCustomerId()));
              return orderDto;
            })
        .collect(Collectors.toList());
  }
}
