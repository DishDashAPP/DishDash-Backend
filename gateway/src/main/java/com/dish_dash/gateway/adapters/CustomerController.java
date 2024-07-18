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

  @PutMapping()
  @Authentication
  Boolean modifyCustomerProfile(String username, @RequestBody CustomerDto customerDto) {
    return userApi.modifyCustomerProfile(Long.parseLong(username), customerDto);
  }

  @PostMapping()
  @Authentication
  public CustomerDto getUserProfile(String username) {
    return userApi.getUserProfile(Long.valueOf(username));
  }
}
