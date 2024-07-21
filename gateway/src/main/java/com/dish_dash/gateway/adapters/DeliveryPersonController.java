package com.dish_dash.gateway.adapters;

import com.dishDash.common.dto.DeliveryPersonDto;
import com.dishDash.common.dto.LocationDto;
import com.dishDash.common.enums.DeliveryPersonStatus;
import com.dishDash.common.feign.user.UserApi;
import com.dish_dash.gateway.annotation.Authentication;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/deliveryPerson")
@RequiredArgsConstructor
public class DeliveryPersonController {
  private final UserApi userApi;

  @PutMapping
  @Authentication(userId = "#userId")
  public Boolean modifyDeliveryPersonProfile(
      long userId, @RequestBody DeliveryPersonDto deliveryPersonDto) {
    return userApi.modifyDeliveryPersonProfile(userId, deliveryPersonDto);
  }

  @GetMapping("/status")
  @Authentication(userId = "#userId")
  public DeliveryPersonStatus getDeliveryPersonStatus(long userId) {
    return userApi.getDeliveryPersonStatus(userId);
  }

  @PostMapping("/location")
  @Authentication
  public boolean setLocation(@RequestBody LocationDto locationDto, String username) {
    return userApi.setLocation(locationDto, Long.parseLong(username));
  }

  @GetMapping("/location")
  @Authentication(userId = "#userId")
  public LocationDto getLocation(long userId) {
    return userApi.getLocation(userId);
  }
}
