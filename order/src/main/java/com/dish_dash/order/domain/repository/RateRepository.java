package com.dish_dash.order.domain.repository;

import com.dish_dash.order.domain.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate, String> {
    Optional<Rate> findById(String id);

    default boolean createReview(String customerID, String orderID, int point) {
        try {
            Rate newRate = new Rate();
            newRate.setCustomerID(customerID);
            newRate.setOrderID(orderID);
            newRate.setPoint(point);
            save(newRate);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    Rate findByDeliveryPersonID(String deliveryPersonID);
}