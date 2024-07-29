package com.dish_dash.gateway.adapters;

import com.dishDash.common.dto.RestaurantOwnerDto;
import com.dishDash.common.feign.user.UserApi;
import com.dish_dash.gateway.annotation.Authentication;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/restaurantOwner")
@RequiredArgsConstructor
public class RestaurantOwnerController {
  private final UserApi userApi;

  @PutMapping
  @Authentication(userId = "#userId")
  Boolean modifyRestaurantProfile(Long userId, @RequestBody RestaurantOwnerDto restaurantOwnerDto) {
    return userApi.modifyRestaurantProfile(userId, restaurantOwnerDto);
  }

  @GetMapping()
  @Authentication(userId = "#userId")
  public RestaurantOwnerDto getUserProfile(Long userId) {
    return userApi.getRestaurantOwnerProfile(userId);
  }
}
