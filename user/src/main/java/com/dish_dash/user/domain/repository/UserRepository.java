package com.dish_dash.user.domain.repository;

import com.dish_dash.user.domain.model.User;

public interface UserRepository<T extends User> {
    T modifyProfile(T user);
    T createUser(T user);
}
