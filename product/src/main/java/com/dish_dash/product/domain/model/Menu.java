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
    private Long menuID; // Change to Long

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<Food> foodList;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "menu_category",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    private String restaurantID;

    public Menu(String restaurantID) {
        this.restaurantID = restaurantID;
        this.menuID = generateMenuID();
    }

    private Long generateMenuID() {
        return System.currentTimeMillis();
    }

    public boolean addFood(Food food) {
        food.setMenu(this); // Ensure the bidirectional relationship is set
        return foodList.add(food);
    }

    public boolean addCategory(Category category) {
        return categories.add(category);
    }
}
