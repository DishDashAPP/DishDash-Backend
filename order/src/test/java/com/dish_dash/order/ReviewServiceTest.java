package com.dish_dash.order;

import com.dish_dash.order.application.service.ReviewService;
import com.dish_dash.order.domain.model.Review;
import com.dish_dash.order.domain.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ReviewServiceTest {

  @MockBean private ReviewRepository reviewRepository;

  @Autowired private ReviewService reviewService;

  @Test
  void setOrderReview_ShouldSaveReviewSuccessfully() {

    boolean result = reviewService.setOrderReview(1L, 1L, "Great service!");

    assertTrue(result, "Review should be saved successfully");

    verify(reviewRepository, times(1)).save(any(Review.class));
  }
}
