package com.dish_dash.product.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Food {
    private String foodID;
    private String name;
    private String description;
    private Price price;
    private int stock;
    private String menuID;

    public Food(String name, String description, double price, int stock, String menuID) {
        this.name = name;
        this.description = description;
        this.price = new Price(price, CurrencyUnit.TOMAN);
        this.stock = stock;
        this.menuID = menuID;
        this.foodID = generateFoodID();
    }

    private String generateFoodID() {
        // Logic to generate food ID
        return "FOOD-" + System.currentTimeMillis();
    }
}
