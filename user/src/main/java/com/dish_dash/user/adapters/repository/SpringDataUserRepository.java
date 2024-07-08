package com.dish_dash.user.adapters.repository;

import com.dish_dash.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataUserRepository extends JpaRepository<User, String> {
}

