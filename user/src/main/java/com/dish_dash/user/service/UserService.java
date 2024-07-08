package com.dish_dash.user.service;

import com.dish_dash.user.domain.model.User;
import com.dish_dash.user.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User modifyProfile(User user) {
        return userRepository.save(user);
    }
}
