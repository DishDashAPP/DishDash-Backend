package com.dish_dash.product.infrastructure.repository;

import com.dish_dash.product.domain.model.Food;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository {
    Food findByID(String foodID);
    Food create(String name, String description, double price, int stock, String menuID);
    boolean modify(Food food);
    boolean delete(String foodID);
}