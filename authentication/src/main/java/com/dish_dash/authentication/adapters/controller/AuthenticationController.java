package com.dish_dash.authentication.adapters.controller;

import com.dishDash.common.feign.authentication.AuthenticationApi;
import com.dish_dash.authentication.application.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationApi {

  private final AuthenticationService authService;

  @Override
  public String login(String username, String password) {
    return authService.login(username, password);
  }

  @Override
  public String validate(String token) {
    return authService.validateToken(token);
  }

  @Override
  public void logout(String token) {
    authService.logout(token);
  }

  @Override
  public void register(String username, String password, String roles) {
    authService.register(username, password, roles);
  }
}
