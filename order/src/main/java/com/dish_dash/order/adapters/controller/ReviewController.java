package com.dish_dash.order.adapters.controller;

import com.dish_dash.order.application.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

  private final ReviewService reviewService;

  @PostMapping("/setOrderReview")
  public boolean setOrderReview(
      @RequestParam Long customerID, @RequestParam Long orderID, @RequestParam String comment) {
    return reviewService.setOrderReview(customerID, orderID, comment);
  }
}
