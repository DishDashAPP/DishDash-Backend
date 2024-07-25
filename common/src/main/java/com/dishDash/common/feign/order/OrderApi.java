package com.dishDash.common.feign.order;

import com.dishDash.common.dto.OrderDto;
import com.dishDash.common.enums.OrderStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "order-service",contextId = "order-service")
public interface OrderApi {
  @GetMapping()
  OrderDto viewOrder(@RequestParam long orderID);

  @GetMapping("/status")
  OrderStatus getOrderStatus(@RequestParam long orderID);

  @PutMapping("")
  boolean prepareOrder(@RequestParam OrderDto order);
}
