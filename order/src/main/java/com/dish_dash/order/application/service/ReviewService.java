package com.dish_dash.order.application.service;

import com.dish_dash.order.domain.model.Review;
import com.dish_dash.order.domain.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final ReviewRepository reviewRepository;

  public boolean setOrderReview(long customerID, long orderID, String comment) {
//    reviewRepository.save(
//        Review.builder().customerId(customerID).orderId(orderID).comment(comment).build());
    return true;
  }
}
