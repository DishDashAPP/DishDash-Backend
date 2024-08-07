package com.dish_dash.gateway.adapters;

import com.dishDash.common.dto.OrderDto;
import com.dishDash.common.dto.OrderItemCreateDto;
import com.dishDash.common.dto.TransactionDto;
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
  public TransactionDto createOrder(
      Long userId,
      @RequestParam Long restaurantOwnerId) {
    return customerOrderAPi.createOrder(userId, restaurantOwnerId);
  }

  @PostMapping("/orderRate")
  @Authentication(userId = "#userId")
  boolean setOrderRate(Long userId, @RequestParam Long orderId, @RequestParam int point) {
    return customerOrderAPi.setOrderRate(userId, orderId, point);
  }

  @PostMapping("/deliveryRate")
  @Authentication(userId = "#userId")
  boolean setDeliveryRate(Long userId, @RequestParam Long orderId, @RequestParam int point) {
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
