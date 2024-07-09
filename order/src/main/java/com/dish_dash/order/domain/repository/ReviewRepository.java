package com.dish_dash.order.domain.repository;

import com.dish_dash.order.domain.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    Optional<Review> findById(String id);

    default boolean createReview(String customerID, String orderID, String comment) {
        Review review = new Review();
        review.setCustomerID(customerID);
        review.setOrderID(orderID);
        review.setComment(comment);
        return save(review) != null;
    }
}
