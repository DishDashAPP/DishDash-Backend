package com.dish_dash.authentication.infrastructure.repository;


import com.dish_dash.authentication.domain.model.AuthenticationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends JpaRepository<AuthenticationInfo, Long> {
    AuthenticationInfo findByUsername(String username);
    boolean create(String username, String password);
    boolean register(String username, String password, String name);
}

