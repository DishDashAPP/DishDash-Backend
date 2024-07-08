package com.dish_dash.user.adapters.repository;

import com.dish_dash.user.domain.model.Customer;
import com.dish_dash.user.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String>, UserRepository<Customer> {
    Customer findByUsername(String username);
    Customer findByPhoneNumber(String phoneNumber);
}
