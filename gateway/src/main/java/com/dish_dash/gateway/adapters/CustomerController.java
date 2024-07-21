package com.dish_dash.gateway.adapters;

import com.dishDash.common.dto.CustomerDto;
import com.dishDash.common.feign.user.UserApi;
import com.dish_dash.gateway.annotation.Authentication;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
  private final UserApi userApi;

  @PutMapping
  @Authentication(userId = "#userId")
  Boolean modifyCustomerProfile(long userId, @RequestBody CustomerDto customerDto) {
    return userApi.modifyCustomerProfile(userId, customerDto);
  }

  @GetMapping()
  @Authentication(userId = "#userId")
  public CustomerDto getUserProfile(long userId) {
    return userApi.getUserProfile(userId);
  }
}
