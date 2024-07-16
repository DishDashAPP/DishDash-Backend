package com.dish_dash.authentication.infrastructure.repository;


import com.dish_dash.authentication.domain.model.AuthenticationInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository {
    AuthenticationInfo findByUsername(String username);
    boolean create(String username, String password);
    boolean register(String username, String password, String name);
}

