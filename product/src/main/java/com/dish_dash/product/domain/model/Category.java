package com.dish_dash.product.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String categoryID;
    private String name;

    public Category(String name) {
        this.name = name;
        this.categoryID = generateCategoryID();
    }

    private String generateCategoryID() {
        return "CAT-" + System.currentTimeMillis();
    }
}
