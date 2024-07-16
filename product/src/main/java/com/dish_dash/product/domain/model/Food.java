package com.dish_dash.product.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String foodID;
    private String name;
    private String description;
    @Embedded
    private Price price;
    private int stock;
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    public Food(String name, String description, double price, int stock, Menu menu) {
        this.name = name;
        this.description = description;
        this.price = new Price(price, CurrencyUnit.TOMAN);
        this.stock = stock;
        this.menu = menu;
        this.foodID = generateFoodID();
    }

    private String generateFoodID() {
        return "FOOD-" + System.currentTimeMillis();
    }
}
