package com.dish_dash.user.adapters.controller;

import com.dish_dash.user.domain.model.User;
import com.dish_dash.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/modify")
    public User modifyUserProfile(@RequestBody User user) {
        return userService.modifyProfile(user);
    }
}