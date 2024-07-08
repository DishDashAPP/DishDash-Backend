package com.dish_dash.user.domain.repository;


import com.dish_dash.user.domain.model.User;

public interface UserRepository {
    User save(User user);
    User findById(String id);
}
