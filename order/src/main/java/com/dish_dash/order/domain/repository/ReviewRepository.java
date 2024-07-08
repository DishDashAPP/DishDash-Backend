package com.dish_dash.order.domain.repository;

import com.dish_dash.order.domain.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    Review findById(String id);

    default boolean createReview(String customerID, String orderID, String comment) {
        Review review = new Review();
        review.setCustomerID(customerID);
        review.setOrderID(orderID);
        review.setComment(comment);
        return save(review) != null;
    }
}
