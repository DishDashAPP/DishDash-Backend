package com.dish_dash.order.adapters.controller;

import com.dishDash.common.feign.order.ReviewApi;
import com.dish_dash.order.application.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController implements ReviewApi {
  private final ReviewService reviewService;

  @Override
  public boolean setOrderReview(Long customerID, Long orderID, String comment) {
    return reviewService.setOrderReview(customerID, orderID, comment);
  }
}
