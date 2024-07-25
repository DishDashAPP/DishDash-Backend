package com.dish_dash.gateway.adapters;

import com.dishDash.common.dto.OrderDto;
import com.dishDash.common.dto.OrderItemCreateDto;
import com.dishDash.common.dto.OrderItemDto;
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
  @Authentication
  public OrderDto createOrder(
      Long userId,
      @RequestParam Long restaurantOwnerId,
      @RequestBody List<OrderItemCreateDto> orderItemsDto) {
    return customerOrderAPi.createOrder(userId, restaurantOwnerId, orderItemsDto);
  }

  @PostMapping("/modifyOrder")
  @Authentication
  OrderDto modifyOrder(@RequestParam Long orderId, @RequestBody List<OrderItemCreateDto> orderItems) {
    return customerOrderAPi.modifyOrder(orderId, orderItems);
  }

  @PostMapping("/orderRate")
  @Authentication
  boolean setOrderRate(long userId, @RequestParam long orderId, @RequestParam int point) {
    return customerOrderAPi.setOrderRate(userId, orderId, point);
  }

  @PostMapping("/deliveryRate")
  @Authentication
  boolean setDeliveryRate(long userId, @RequestParam long orderId, @RequestParam int point) {
    return customerOrderAPi.setDeliveryRate(userId, orderId, point);
  }

  @GetMapping("/customerOrders")
  @Authentication
  List<OrderDto> getCustomerOrders(Long userId) {
    return customerOrderAPi.getCustomerOrders(userId);
  }

  @GetMapping("/current")
  @Authentication
  OrderDto getCustomerCurrentOrder(long userId) {
    return customerOrderAPi.getCustomerCurrentOrder(userId);
  }
}
