package com.dish_dash.authentication.adapters.controller;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public Token login(@RequestParam String username, @RequestParam String password) {
        return authenticationService.login(username, password);
    }

    @PostMapping("/logout")
    public boolean logout(@RequestParam String tokenID) {
        return authenticationService.logout(tokenID);
    }

    @PostMapping("/register")
    public User register(@RequestParam String username, @RequestParam String password, @RequestParam String name) {
        return authenticationService.register(username, password, name);
    }
}
