package com.dishDash.common.feign.authentication;

import com.dishDash.common.dto.AuthDto;
import com.dishDash.common.enums.Role;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "authentication-service")
public interface AuthenticationApi {

  @PostMapping("/login")
  String login(@RequestParam String username, @RequestParam String password);

  @GetMapping("/validate")
  AuthDto validate(@RequestParam String token);

  @PostMapping("/logout")
  void logout(@RequestParam String token);

  @PostMapping("/register")
  void register(
      @RequestParam String username, @RequestParam String password, @RequestParam Role roles);
}
