package com.dish_dash.order.domain.repository;

import com.dish_dash.order.domain.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<Rate, String> {

  //  Rate findByDeliveryPersonID(String deliveryPersonID);
}
