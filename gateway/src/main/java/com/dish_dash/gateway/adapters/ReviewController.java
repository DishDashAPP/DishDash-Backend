package com.dish_dash.gateway.adapters;

import com.dishDash.common.feign.order.ReviewApi;
import com.dish_dash.gateway.annotation.Authentication;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/review")
@RequiredArgsConstructor
public class ReviewController {
  private final ReviewApi reviewApi;

  @PostMapping("/order")
  @Authentication
  public boolean setOrderReview(
      String username, @RequestParam Long orderID, @RequestParam String comment) {
    return reviewApi.setOrderReview(Long.valueOf(username), orderID, comment);
  }
}
