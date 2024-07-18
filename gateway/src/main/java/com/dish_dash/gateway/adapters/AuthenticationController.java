package com.dish_dash.gateway.adapters;

import com.dishDash.common.feign.authentication.AuthenticationApi;
import com.dish_dash.gateway.annotation.Authentication;
import com.dish_dash.gateway.request.LoginRequest;
import com.dish_dash.gateway.request.RegisterRequest;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping("/validate")
  @Authentication
  boolean validate(String token) {
    return Objects.nonNull(token);
  }

  @PostMapping("/logout")
  @Authentication
  public void logout(String token) {
    authenticationApi.logout(token);
  }
}
