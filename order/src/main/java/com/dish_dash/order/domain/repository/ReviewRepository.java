package com.dish_dash.order.domain.repository;

import com.dish_dash.order.domain.model.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
  List<Review> findAllByOrderId(long orderId);
}
