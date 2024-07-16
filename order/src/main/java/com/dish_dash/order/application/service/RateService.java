package com.dish_dash.order.application.service;

import com.dish_dash.order.domain.model.Rate;
import com.dish_dash.order.domain.repository.RateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateService {

  private final RateRepository rateRepository;

  public boolean setOrderRate(Long customerId, Long orderId, int point) {
//    Rate rate = Rate.builder().customerId(customerId).orderId(orderId).point(point).build();
//    rateRepository.save(rate);
    return true;
  }

  public boolean setDeliveryRate(Long customerId, Long orderId, int point) {
//    Rate rate = Rate.builder().customerId(customerId).orderId(orderId).point(point).build();
//    rateRepository.save(rate);
    return true;
  }

  public Rate getDeliveryRate(String deliveryPersonID) {
    // TODO rate, no delivery person

    // return rateRepository.findByDeliveryPersonID(deliveryPersonID);
    return Rate.builder().build();
  }
}
