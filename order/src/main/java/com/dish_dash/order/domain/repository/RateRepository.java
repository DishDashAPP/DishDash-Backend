package com.dish_dash.order.domain.repository;

import com.dish_dash.order.domain.model.Rate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<Rate, String> {
  List<Rate> findAllByDeliveryPersonID(String deliveryPersonID);

  Rate findByCustomerIdAndOrderId(Long customerId, Long orderId);
  //  Rate findByDeliveryPersonID(String deliveryPersonID);
}
