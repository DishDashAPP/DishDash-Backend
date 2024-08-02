package com.dish_dash.order.application.service;

import com.dishDash.common.dto.OrderDto;
import com.dishDash.common.dto.OrderItemDto;
import com.dishDash.common.enums.ErrorCode;
import com.dishDash.common.enums.OrderStatus;
import com.dishDash.common.exception.CustomException;
import com.dishDash.common.feign.Product.FoodApi;
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
  private final FoodApi foodApi;

  public boolean updateOrderStatusByRestaurantOwner(
      Long restaurantOwnerId, long orderId, OrderStatus status) {
    Optional<Order> orderOptional = orderRepository.findById(orderId);
    if (orderOptional.isPresent()) {
      if (orderOptional.get().getRestaurantOwnerId() != restaurantOwnerId)
        throw new CustomException(ErrorCode.FORBIDDEN, "Restaurant owner id mismatch");

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
        .map(this::mapOrderToOrderDto)
        .collect(Collectors.toList());
  }

  public List<OrderDto> getRestaurantOwnerActiveOrders(long restaurantOwnerID) {
    return orderRepository
        .findAllByRestaurantOwnerIdAndStatusIn(
            restaurantOwnerID, List.of(OrderStatus.PREPARING, OrderStatus.DELIVERING))
        .stream()
        .map(this::mapOrderToOrderDto)
        .collect(Collectors.toList());
  }

  private OrderDto mapOrderToOrderDto(Order order) {
    OrderDto orderDto = OrderMapper.INSTANCE.orderToDto(order);
    orderDto.setDeliveryPersonDto(userApi.getDeliveryPersonProfile(order.getDeliveryPersonId()));
    orderDto.setCustomerDto(userApi.getCustomerProfile(order.getCustomerId()));

    for (OrderItemDto orderItemDto : orderDto.getOrderItems())
      orderItemDto.setName(foodApi.getFoodById(orderItemDto.getFoodId()).getName());

    return orderDto;
  }
}
