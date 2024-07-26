package com.dish_dash.order.adapters.controller;

import com.dishDash.common.dto.RateDto;
import com.dishDash.common.feign.order.RateApi;
import com.dish_dash.order.application.service.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rates")
@RequiredArgsConstructor
public class RateController implements RateApi {
  private final RateService rateService;

  @Override
  public boolean setOrderRate(long customerId, long orderId, int point) {
    return rateService.setOrderRate(customerId, orderId, point);
  }

  @Override
  public boolean setDeliveryRate(long customerID, long orderID, int point) {
    return rateService.setDeliveryRate(customerID, orderID, point);
  }

  @Override
  public RateDto getDeliveryRate(String deliveryPersonID) {
    return rateService.getDeliveryRate(deliveryPersonID);
  }
}
