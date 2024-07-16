package com.dish_dash.product.infrastructure.repository;

import com.dish_dash.product.domain.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
}
