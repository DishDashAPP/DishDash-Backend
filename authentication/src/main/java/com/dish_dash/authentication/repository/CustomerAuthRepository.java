package com.dish_dash.authentication.repository;

import com.dish_dash.authentication.model.entity.CustomerAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAuthRepository extends JpaRepository<CustomerAuth, Long> {}
