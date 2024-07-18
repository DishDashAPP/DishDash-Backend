package com.dishDash.common.feign.order;

import com.dishDash.common.dto.OrderDto;
import com.dishDash.common.dto.OrderItemDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "order-service", contextId = "customer-order-service")
public interface CustomerOrderAPi {
  @PostMapping("/createOrder")
  OrderDto createOrder(
      @RequestParam Long customerId,
      @RequestParam Long restaurantOwnerId,
      @RequestBody List<OrderItemDto> orderItemsDto);

  @PostMapping("/modifyOrder")
  OrderDto modifyOrder(@RequestParam Long orderId, @RequestBody List<OrderItemDto> orderItems);

  @PostMapping("/setOrderRate")
  boolean setOrderRate(
      @RequestParam Long customerId, @RequestParam Long orderId, @RequestParam int point);

  @PostMapping("/setDeliveryRate")
  boolean setDeliveryRate(
      @RequestParam Long customerId, @RequestParam Long orderId, @RequestParam int point);

  @GetMapping("/getCustomerOrders")
  List<OrderDto> getCustomerOrders(@RequestParam Long customerId);

  @GetMapping("/getCustomerCurrentOrder")
  OrderDto getCustomerCurrentOrder(@RequestParam Long customerId);
}
