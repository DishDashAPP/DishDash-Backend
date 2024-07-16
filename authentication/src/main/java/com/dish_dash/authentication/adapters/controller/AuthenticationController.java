package com.dish_dash.authentication.adapters.controller;


import com.dish_dash.authentication.application.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authService;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        return authService.login(username, password);
    }

    @GetMapping("/validate")
    public boolean validate(@RequestParam String token) {
        return authService.validateToken(token);
    }

    @PostMapping("/logout")
    public void logout(@RequestParam String token) {
        authService.logout(token);
    }

    @PostMapping("/register")
    public void register(@RequestParam String username, @RequestParam String password, @RequestParam String roles) {
        authService.register(username, password, roles);
    }
}
