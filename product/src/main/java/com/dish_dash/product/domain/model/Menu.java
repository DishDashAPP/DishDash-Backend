package com.dish_dash.product.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String menuID;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
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
