package com.dish_dash.authentication.adapters.controller;

import com.dishDash.common.dto.AuthDto;
import com.dishDash.common.enums.Role;
import com.dishDash.common.feign.authentication.AuthenticationApi;
import com.dish_dash.authentication.application.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationApi {

  private final AuthenticationService authService;

  @Override
  public String login(String username, String password) {
    return authService.login(username, password);
  }

  @Override
  public AuthDto validate(String token) {
    return authService.validateToken(token);
  }

  @Override
  public void logout(String token) {
    authService.invalidateToken(token);
  }

  @Override
  public void register(String username, String password, Role role) {
    authService.register(username, password, role);
  }
}
