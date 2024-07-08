package com.dish_dash.order.application.service;

import com.dish_dash.order.domain.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public boolean setOrderReview(String customerID, String orderID, String comment) {
        return reviewRepository.createReview(customerID, orderID, comment);
    }
}
