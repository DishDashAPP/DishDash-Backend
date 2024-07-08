package com.dish_dash.product.application.service;

import com.dish_dash.product.domain.model.Food;
import com.dish_dash.product.infrastructure.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    public boolean addFoodItem(String name, String description, double price, int stock, String menuID) {
        Food food = new Food(name, description, price, stock, menuID);
        return foodRepository.create(food) != null;
    }

    public boolean deleteFood(String foodID) {
        return foodRepository.delete(foodID);
    }

    public boolean modifyFood(Food food) {
        return foodRepository.modify(food);
    }
}
