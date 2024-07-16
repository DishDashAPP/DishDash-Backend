package com.dish_dash.product.application.service;

import com.dish_dash.product.domain.model.Food;
import com.dish_dash.product.domain.model.Menu;
import com.dish_dash.product.infrastructure.repository.FoodRepository;
import com.dish_dash.product.infrastructure.repository.MenuRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

  private final MenuRepository menuRepository;

  private final FoodRepository foodRepository;

  public List<Menu> getAllMenus() {
    return menuRepository.findAll();
  }

  public Menu getMenuById(Long id) {
    return menuRepository.findById(id).orElse(null);
  }

  public Menu saveMenu(Menu menu) {
    return menuRepository.save(menu);
  }

  public void deleteMenu(Long id) {
    menuRepository.deleteById(id);
  }

  public Food addFoodToMenu(Long menuId, Food food) {
    Menu menu =
        menuRepository
            .findById(menuId)
            .orElseThrow(() -> new IllegalArgumentException("Menu not found"));
    food.setMenu(menu);
    return foodRepository.save(food);
  }
}
