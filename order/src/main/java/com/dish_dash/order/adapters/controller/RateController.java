package com.dish_dash.order.adapters.controller;

import com.dish_dash.order.application.service.RateService;
import com.dish_dash.order.domain.model.Rate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rates")
@RequiredArgsConstructor
public class RateController {

  private final RateService rateService;

  @PostMapping("/order")
  public boolean setOrderRate(
      @RequestParam Long customerId, @RequestParam Long orderId, @RequestParam int point) {
    return rateService.setOrderRate(customerId, orderId, point);
  }

  @PostMapping("/delivery")
  public boolean setDeliveryRate(
      @RequestParam Long customerID, @RequestParam Long orderID, @RequestParam int point) {
    return rateService.setDeliveryRate(customerID, orderID, point);
  }

  @GetMapping("/delivery/{deliveryPersonID}")
  public Rate getDeliveryRate(@PathVariable String deliveryPersonID) {
    return rateService.getDeliveryRate(deliveryPersonID);
  }
}
