package com.dish_dash.user.adapters.repository;

import com.dish_dash.user.domain.model.RestaurantOwner;
import com.dish_dash.user.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantOwnerRepository extends JpaRepository<RestaurantOwner, String>, UserRepository<RestaurantOwner> {
    RestaurantOwner findByUsername(String username);
}
