package com.dish_dash.gateway.adapters;

import com.dishDash.common.dto.OrderDto;
import com.dishDash.common.dto.OrderItemDto;
import com.dishDash.common.feign.order.CustomerOrderAPi;
import com.dish_dash.gateway.annotation.Authentication;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order/customer")
@RequiredArgsConstructor
public class CustomerOrderController {
  private final CustomerOrderAPi customerOrderAPi;

  @PostMapping
  @Authentication(userId = "#userId")
  public OrderDto createOrder(
      long userId,
      @RequestParam Long restaurantOwnerId,
      @RequestBody List<OrderItemDto> orderItemsDto) {
    return customerOrderAPi.createOrder(userId, restaurantOwnerId, orderItemsDto);
  }

  @PostMapping("/modifyOrder")
  @Authentication
  OrderDto modifyOrder(@RequestParam Long orderId, @RequestBody List<OrderItemDto> orderItems) {
    return customerOrderAPi.modifyOrder(orderId, orderItems);
  }

  @PostMapping("/orderRate")
  @Authentication(userId = "#userId")
  boolean setOrderRate(long userId, @RequestParam Long orderId, @RequestParam int point) {
    return customerOrderAPi.setOrderRate(userId, orderId, point);
  }

  @PostMapping("/deliveryRate")
  @Authentication(userId = "#userId")
  boolean setDeliveryRate(long userId, @RequestParam Long orderId, @RequestParam int point) {
    return customerOrderAPi.setDeliveryRate(userId, orderId, point);
  }

  @GetMapping("/customerOrders")
  @Authentication(userId = "#userId")
  List<OrderDto> getCustomerOrders(long userId) {
    return customerOrderAPi.getCustomerOrders(userId);
  }

  @GetMapping("/current")
  @Authentication(userId = "#userId")
  OrderDto getCustomerCurrentOrder(long userId) {
    return customerOrderAPi.getCustomerCurrentOrder(userId);
  }
}
