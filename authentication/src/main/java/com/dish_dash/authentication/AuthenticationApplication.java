package com.dish_dash.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.dish_dash.authentication")
@EnableJpaRepositories(basePackages = "com.dish_dash.authentication.infrastructure.repository")
public class AuthenticationApplication {
  public static void main(String[] args) {
    SpringApplication.run(AuthenticationApplication.class, args);
  }
}
