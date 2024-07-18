package com.dish_dash.gateway.adapters;

import com.dishDash.common.feign.authentication.AuthenticationApi;
import com.dish_dash.gateway.annotation.Authentication;
import com.dish_dash.gateway.request.LoginRequest;
import com.dish_dash.gateway.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationApi authenticationApi;

  @PostMapping("/login")
  public String login(@RequestBody LoginRequest loginRequest) {
    return authenticationApi.login(loginRequest.getUsername(), loginRequest.getPassword());
  }

  @PostMapping("/register")
  public void register(@RequestBody RegisterRequest registerRequest) {
    authenticationApi.register(
        registerRequest.getUsername(),
        registerRequest.getPassword(),
        // TODO REMOVE .toString()
        registerRequest.getRole().toString());
  }

  @PostMapping("/logout")
  @Authentication
  public void logout(String token) {
    authenticationApi.logout(token);
  }
}
