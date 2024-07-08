package com.dish_dash.product.infrastructure.repository;

import com.dish_dash.product.domain.model.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository {
    Category findByID(String categoryID);
    Category create(String name);
}
