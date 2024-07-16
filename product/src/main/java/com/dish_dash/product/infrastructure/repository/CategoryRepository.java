package com.dish_dash.product.infrastructure.repository;

import com.dish_dash.product.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository  extends JpaRepository<Category, Long> {
    Category findByCategoryID(Long categoryID);
}
