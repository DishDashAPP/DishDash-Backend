package com.dishDash.common.feign.authentication;

import com.dishDash.common.dto.AuthDto;
import com.dishDash.common.enums.Role;
import com.dishDash.common.response.LoginResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "authentication-service")
public interface AuthenticationApi {

  @PostMapping("auth/login")
  LoginResponse login(@RequestParam String username, @RequestParam String password);

  @GetMapping("auth/validate")
  AuthDto validate(@RequestParam String token);

  @GetMapping("auth/info")
  String getUsername(@RequestParam Long userId);

  @PostMapping("auth/logout")
  void logout(@RequestParam String token);

  @PostMapping("auth/register")
  void register(
      @RequestParam String username, @RequestParam String password, @RequestParam Role role);
}
