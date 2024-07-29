package com.dishDash.common.feign.order;

import com.dishDash.common.dto.OrderDto;
import com.dishDash.common.dto.OrderItemCreateDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "order-service", contextId = "customer-order-service", path = "/order/customer")
public interface CustomerOrderAPi {
  @PostMapping("/createOrder")
  OrderDto createOrder(
      @RequestParam Long customerId,
      @RequestParam Long restaurantOwnerId,
      @RequestBody List<OrderItemCreateDto> orderItemsDto);

  @PostMapping("/modifyOrder")
  OrderDto modifyOrder(
      @RequestParam Long customerId,
      @RequestParam Long orderId,
      @RequestBody List<OrderItemCreateDto> orderItems);

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
