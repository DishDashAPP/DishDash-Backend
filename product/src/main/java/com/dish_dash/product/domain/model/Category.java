package com.dish_dash.product.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Category {
    private String categoryID;
    private String name;

    public Category(String name) {
        this.name = name;
        this.categoryID = generateCategoryID();
    }

    private String generateCategoryID() {
        // Logic to generate category ID
        return "CAT-" + System.currentTimeMillis();
    }
}
