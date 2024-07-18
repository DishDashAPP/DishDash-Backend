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

  @PutMapping()
  @Authentication
  public Boolean modifyDeliveryPersonProfile(
      String username, @RequestBody DeliveryPersonDto deliveryPersonDto) {
    return userApi.modifyDeliveryPersonProfile(Long.parseLong(username), deliveryPersonDto);
  }

  @GetMapping("/status")
  @Authentication
  public DeliveryPersonStatus getDeliveryPersonStatus(String username) {
    return userApi.getDeliveryPersonStatus(Long.parseLong(username));
  }

  @PostMapping("/location")
  @Authentication
  public boolean setLocation(@RequestBody LocationDto locationDto, String username) {
    return userApi.setLocation(locationDto, Long.parseLong(username));
  }

  @GetMapping("/location")
  @Authentication
  public LocationDto getLocation(String username) {
    return userApi.getLocation(Long.parseLong(username));
  }
}
