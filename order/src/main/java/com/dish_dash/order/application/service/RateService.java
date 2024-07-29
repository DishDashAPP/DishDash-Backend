package com.dish_dash.order.application.service;

import com.dishDash.common.dto.RateDto;
import com.dish_dash.order.domain.mapper.OrderMapper;
import com.dish_dash.order.domain.model.Rate;
import com.dish_dash.order.domain.repository.RateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateService {

  private final RateRepository rateRepository;

  public boolean setOrderRate(long customerId, long orderId, int point) {
        Rate rate = Rate.builder().customerId(customerId).orderId(orderId).point(point).build();
        return rateRepository.save(rate).getId() > 0;
  }

  public boolean setDeliveryRate(long customerId, long orderId, int point) {
        Rate rate = Rate.builder().customerId(customerId).orderId(orderId).point(point).build();
        return rateRepository.save(rate).getId() > 0;
  }

  public RateDto getDeliveryRate(String deliveryPersonID) {
    // TODO rate, no delivery person
    return OrderMapper.INSTANCE.rateToRateDto(rateRepository.findByDeliveryPersonID(deliveryPersonID));
  }
}
