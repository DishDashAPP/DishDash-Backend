package com.dishDash.common.feign.order;

import com.dishDash.common.dto.RateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "order-service", contextId = "rate-order-service")
public interface RateApi {
  @PostMapping("/order")
  boolean setOrderRate(
      @RequestParam Long customerId, @RequestParam Long orderId, @RequestParam int point);

  @PostMapping("/delivery")
  boolean setDeliveryRate(
      @RequestParam Long customerID, @RequestParam Long orderID, @RequestParam int point);

  @GetMapping("/delivery/{deliveryPersonID}")
  RateDto getDeliveryRate(@PathVariable String deliveryPersonID);
}
