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

  @PostMapping()
  @Authentication
  public OrderDto createOrder(
      String username,
      @RequestParam Long restaurantOwnerId,
      @RequestBody List<OrderItemDto> orderItemsDto) {
    return customerOrderAPi.createOrder(Long.valueOf(username), restaurantOwnerId, orderItemsDto);
  }

  @PostMapping("/modifyOrder")
  @Authentication
  OrderDto modifyOrder(@RequestParam Long orderId, @RequestBody List<OrderItemDto> orderItems) {
    return customerOrderAPi.modifyOrder(orderId, orderItems);
  }

  @PostMapping("/orderRate")
  @Authentication
  boolean setOrderRate(String username, @RequestParam Long orderId, @RequestParam int point) {
    return customerOrderAPi.setOrderRate(Long.valueOf(username), orderId, point);
  }

  @PostMapping("/deliveryRate")
  @Authentication
  boolean setDeliveryRate(String username, @RequestParam Long orderId, @RequestParam int point) {
    return customerOrderAPi.setDeliveryRate(Long.valueOf(username), orderId, point);
  }

  @GetMapping("/customerOrders")
  @Authentication
  List<OrderDto> getCustomerOrders(String username) {
    return customerOrderAPi.getCustomerOrders(Long.valueOf(username));
  }

  @GetMapping("/current")
  @Authentication
  OrderDto getCustomerCurrentOrder(String username) {
    return customerOrderAPi.getCustomerCurrentOrder(Long.valueOf(username));
  }
}
