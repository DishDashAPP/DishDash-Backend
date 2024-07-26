package com.dish_dash.gateway.adapters;

import com.dishDash.common.dto.CustomerDto;
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
      Long userId, @RequestBody DeliveryPersonDto deliveryPersonDto) {
    return userApi.modifyDeliveryPersonProfile(userId, deliveryPersonDto);
  }

  @GetMapping("/status")
  @Authentication(userId = "#userId")
  public DeliveryPersonStatus getDeliveryPersonStatus(Long userId) {
    return userApi.getDeliveryPersonStatus(userId);
  }

  @PostMapping("/location")
  @Authentication(userId = "#userId")
  public boolean setLocation(Long userId, @RequestBody LocationDto locationDto) {
    return userApi.setLocation(locationDto, userId);
  }

  @GetMapping("/location")
  @Authentication(userId = "#userId")
  public LocationDto getLocation(Long userId) {
    return userApi.getLocation(userId);
  }
  @GetMapping()
  @Authentication(userId = "#userId")
  public DeliveryPersonDto getUserProfile(Long userId) {
    return userApi.getDeliveryPersonProfile(userId);
  }
}
