package com.dish_dash.product.infrastructure.repository;

import com.dish_dash.product.domain.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    Optional<Menu> findByRestaurantId(Long aLong);
}
