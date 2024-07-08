package com.dish_dash.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.dish_dash.authentication")
public class AuthenticationApplication {
  public static void main(String[] args) {
    SpringApplication.run(AuthenticationApplication.class, args);
  }
}
