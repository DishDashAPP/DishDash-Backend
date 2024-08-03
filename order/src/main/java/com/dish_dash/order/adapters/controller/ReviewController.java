package com.dish_dash.order.adapters.controller;

import com.dishDash.common.dto.ReviewDto;
import com.dishDash.common.feign.order.ReviewApi;
import com.dish_dash.order.application.service.ReviewService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController implements ReviewApi {
  private final ReviewService reviewService;

  @Override
  public boolean setOrderReview(long customerID, long orderID, String comment) {
    return reviewService.setOrderReview(customerID, orderID, comment);
  }

  @Override
  public List<ReviewDto> getRestaurantReview(long restaurantId) {
    return reviewService.getRestaurantReview(restaurantId);
  }
}
