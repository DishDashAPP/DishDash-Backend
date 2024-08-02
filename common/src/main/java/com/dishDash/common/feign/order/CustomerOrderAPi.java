package com.dishDash.common.feign.order;

import com.dishDash.common.dto.OrderDto;

import java.util.List;

import com.dishDash.common.dto.TransactionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "order-service", contextId = "customer-order-service", path = "/order/customer")
public interface CustomerOrderAPi {
  @PostMapping("/createOrder")
  TransactionDto createOrder(
      @RequestParam Long customerId,
      @RequestParam Long restaurantOwnerId);

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
