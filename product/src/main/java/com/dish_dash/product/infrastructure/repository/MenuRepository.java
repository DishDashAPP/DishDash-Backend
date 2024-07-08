package com.dish_dash.product.infrastructure.repository;

import com.dish_dash.product.domain.model.Menu;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository {
    Menu findByID(String menuID);
    Menu create(String restaurantID);
    boolean addCategory(String name);
}
