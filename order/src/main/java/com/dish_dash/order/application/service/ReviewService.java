package com.dish_dash.order.application.service;

import com.dishDash.common.dto.ReviewDto;
import com.dish_dash.order.domain.mapper.OrderMapper;
import com.dish_dash.order.domain.model.Review;
import com.dish_dash.order.domain.repository.ReviewRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final OrderService orderService;

  public boolean setOrderReview(long customerID, long orderID, String comment) {
    reviewRepository.save(
        Review.builder().customerId(customerID).orderId(orderID).comment(comment).build());
    return true;
  }

  public List<ReviewDto> getRestaurantReview(long restaurantId) {
    return orderService.getAllOrderByRestaurantOwnerId(restaurantId).stream()
        .map(
            (order) -> {
              Optional<Review> reviewOptional =
                  reviewRepository.findAllByOrderId(order.getId()).stream().findFirst();
              return reviewOptional.map(OrderMapper.INSTANCE::reviewToReviewDto).orElse(null);
            })
        .toList();
  }
}
