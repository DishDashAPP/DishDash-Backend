package com.dish_dash.gateway.adapters;

import com.dishDash.common.dto.OrderDto;
import com.dishDash.common.dto.OrderItemCreateDto;
import com.dishDash.common.feign.order.CustomerOrderAPi;
import com.dish_dash.gateway.annotation.Authentication;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/order/customer")
@RequiredArgsConstructor
public class CustomerOrderController {
  private final CustomerOrderAPi customerOrderAPi;

  @PostMapping
  @Authentication(userId = "#userId")
  public OrderDto createOrder(
      long userId,
      @RequestParam Long restaurantOwnerId,
      @RequestBody List<OrderItemCreateDto> orderItemsDto) {
    return customerOrderAPi.createOrder(userId, restaurantOwnerId, orderItemsDto);
  }

  @PostMapping("/modifyOrder")
  @Authentication(userId = "#userId")
  OrderDto modifyOrder(
      Long userId, @RequestParam Long orderId, @RequestBody List<OrderItemCreateDto> orderItems) {
    return customerOrderAPi.modifyOrder(userId, orderId, orderItems);
  }

  @PostMapping("/orderRate")
  @Authentication(userId = "#userId")
  boolean setOrderRate(Long userId, @RequestParam long orderId, @RequestParam int point) {
    return customerOrderAPi.setOrderRate(userId, orderId, point);
  }

  @PostMapping("/deliveryRate")
  @Authentication(userId = "#userId")
  boolean setDeliveryRate(long userId, @RequestParam long orderId, @RequestParam int point) {
    return customerOrderAPi.setDeliveryRate(userId, orderId, point);
  }

  @GetMapping("/customerOrders")
  @Authentication(userId = "#userId")
  List<OrderDto> getCustomerOrders(Long userId) {
    return customerOrderAPi.getCustomerOrders(userId);
  }

  @GetMapping("/current")
  @Authentication(userId = "#userId")
  OrderDto getCustomerCurrentOrder(Long userId) {
    return customerOrderAPi.getCustomerCurrentOrder(userId);
  }
}
