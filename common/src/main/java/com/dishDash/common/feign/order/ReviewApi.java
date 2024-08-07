package com.dishDash.common.feign.order;

import com.dishDash.common.dto.ReviewDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "order-service", contextId = "review-order-service", path = "/review")
public interface ReviewApi {
  @PostMapping("/order")
  boolean setOrderReview(
      @RequestParam long customerID, @RequestParam long orderID, @RequestParam String comment);

  @GetMapping
  List<ReviewDto> getRestaurantReview(@RequestParam long restaurantId);
}
