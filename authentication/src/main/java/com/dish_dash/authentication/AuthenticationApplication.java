package com.dish_dash.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.dishDash.common.feign")
@SpringBootApplication(scanBasePackages = {"com.dish_dash.authentication", "com.dishDash.common"})
@EnableEurekaClient
public class AuthenticationApplication {
  public static void main(String[] args) {
    SpringApplication.run(AuthenticationApplication.class, args);
  }
} 
