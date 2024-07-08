package com.dish_dash.product.application.service;

import com.dish_dash.product.domain.model.Menu;
import com.dish_dash.product.infrastructure.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public Menu getMenu(String restaurantOwnerID) {
        return menuRepository.findByID(restaurantOwnerID);
    }

    public Menu createMenu(String restaurantID) {
        return menuRepository.create(restaurantID);
    }

    public boolean addCategory(String name) {
        // Logic to add category to menu
        return true;
    }
}
