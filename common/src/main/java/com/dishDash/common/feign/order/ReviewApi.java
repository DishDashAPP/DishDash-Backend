package com.dishDash.common.feign.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "order-service", contextId = "review-order-service")
public interface ReviewApi {
  @PostMapping("/order")
  boolean setOrderReview(
      @RequestParam long customerID, @RequestParam long orderID, @RequestParam String comment);
}
