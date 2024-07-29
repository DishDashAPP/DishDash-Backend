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
      @RequestParam long customerId,
      @RequestParam long restaurantOwnerId,
      @RequestBody List<OrderItemCreateDto> orderItemsDto);

  @PostMapping("/modifyOrder")
  OrderDto modifyOrder(
      @RequestParam long customerId,
      @RequestParam long orderId,
      @RequestBody List<OrderItemCreateDto> orderItems);

  @PostMapping("/setOrderRate")
  boolean setOrderRate(
      @RequestParam long customerId, @RequestParam long orderId, @RequestParam int point);

  @PostMapping("/setDeliveryRate")
  boolean setDeliveryRate(
      @RequestParam long customerId, @RequestParam long orderId, @RequestParam int point);

  @GetMapping("/getCustomerOrders")
  List<OrderDto> getCustomerOrders(@RequestParam long customerId);

  @GetMapping("/getCustomerCurrentOrder")
  OrderDto getCustomerCurrentOrder(@RequestParam long customerId);
}
