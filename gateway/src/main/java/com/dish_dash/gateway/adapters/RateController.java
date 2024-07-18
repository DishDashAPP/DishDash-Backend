package com.dish_dash.gateway.adapters;

import com.dishDash.common.dto.RateDto;
import com.dishDash.common.feign.order.RateApi;
import com.dish_dash.gateway.annotation.Authentication;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/rate")
@RequiredArgsConstructor
public class RateController {
  private final RateApi rateApi;

  @PostMapping("/order")
  @Authentication
  boolean setOrderRate(String username, @RequestParam Long orderId, @RequestParam int point) {
    return rateApi.setOrderRate(Long.valueOf(username), orderId, point);
  }

  @PostMapping("/delivery")
  @Authentication
  boolean setDeliveryRate(String username, @RequestParam Long orderID, @RequestParam int point) {
    return rateApi.setDeliveryRate(Long.valueOf(username), orderID, point);
  }

  @GetMapping("/delivery/{deliveryPersonID}")
  @Authentication
  RateDto getDeliveryRate(@PathVariable String deliveryPersonID) {
    return rateApi.getDeliveryRate(deliveryPersonID);
  }
}
