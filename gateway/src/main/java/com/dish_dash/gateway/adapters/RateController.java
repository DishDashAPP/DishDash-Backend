package com.dish_dash.gateway.adapters;

import com.dishDash.common.dto.RateDto;
import com.dishDash.common.feign.order.RateApi;
import com.dish_dash.gateway.annotation.Authentication;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/rate")
@RequiredArgsConstructor
public class RateController {
  private final RateApi rateApi;

  @PostMapping("/order")
  @Authentication(userId = "#userId")
  public boolean setOrderRate(Long userId, @RequestParam long orderId, @RequestParam int point) {
    return rateApi.setOrderRate(userId, orderId, point);
  }

  @PostMapping("/delivery")
  @Authentication(userId = "#userId")
  public boolean setDeliveryRate(Long userId, @RequestParam long orderId, @RequestParam int point) {
    return rateApi.setDeliveryRate(userId, orderId, point);
  }

  @GetMapping("/delivery/{deliveryPersonId}")
  @Authentication
  public RateDto getDeliveryRate(@PathVariable String deliveryPersonId) {
    return rateApi.getDeliveryRate(deliveryPersonId);
  }
}
