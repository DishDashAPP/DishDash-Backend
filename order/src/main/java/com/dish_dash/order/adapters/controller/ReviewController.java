package com.dish_dash.order.adapters.controller;

import com.dish_dash.order.application.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/setOrderReview")
    public boolean setOrderReview(@RequestParam String customerID, @RequestParam String orderID, @RequestParam String comment) {
        return reviewService.setOrderReview(customerID, orderID, comment);
    }
}
