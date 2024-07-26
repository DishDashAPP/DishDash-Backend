package com.dishDash.common.feign.order;

import com.dishDash.common.dto.OrderDto;
import com.dishDash.common.enums.OrderStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "order-service", contextId = "delivery-person-order-service")
public interface DeliveryPersonOrderApi {
  @PutMapping("/status")
  boolean updateOrderStatusByDeliveryPerson(
      @RequestParam long orderID, @RequestParam OrderStatus status);

  @GetMapping("/current")
  OrderDto getDeliveryPersonCurrentOrder(@RequestParam String deliveryPersonID);
}
