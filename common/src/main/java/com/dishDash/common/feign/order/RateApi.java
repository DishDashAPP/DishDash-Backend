package com.dishDash.common.feign.order;

import com.dishDash.common.dto.RateDto;
import com.dishDash.common.dto.RestaurantCommentsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "order-service", contextId = "rate-order-service", path = "/rate/order")
public interface RateApi {
  @PostMapping("/order")
  boolean setOrderRate(
      @RequestParam long customerId, @RequestParam long orderId, @RequestParam int point);

  @PostMapping("/delivery")
  boolean setDeliveryRate(
      @RequestParam long customerID, @RequestParam long orderID, @RequestParam int point);

  @GetMapping("/delivery/{deliveryPersonID}")
  RateDto getDeliveryRate(@PathVariable String deliveryPersonID);

  @GetMapping("/info")
  RestaurantCommentsDto getRestaurantComments(long restaurantId);
}
