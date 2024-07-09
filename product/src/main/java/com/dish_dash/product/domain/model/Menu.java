package com.dish_dash.product.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Menu {
    private String menuID;
    private List<Food> foodList;
    private List<Category> categories;
    private String restaurantID;

    public Menu(String restaurantID) {
        this.restaurantID = restaurantID;
        this.menuID = generateMenuID();
    }

    private String generateMenuID() {
        return "MENU-" + System.currentTimeMillis();
    }

    public boolean addFood(Food food) {
        return foodList.add(food);
    }

    public boolean addCategory(Category category) {
        return categories.add(category);
    }
}
