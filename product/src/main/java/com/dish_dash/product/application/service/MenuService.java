package com.dish_dash.product.application.service;

import com.dishDash.common.dto.FoodDto;
import com.dishDash.common.dto.MenuDto;
import com.dish_dash.product.domain.mapper.ProductMapper;
import com.dish_dash.product.domain.model.Food;
import com.dish_dash.product.domain.model.Menu;
import com.dish_dash.product.infrastructure.repository.FoodRepository;
import com.dish_dash.product.infrastructure.repository.MenuRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {
  private final MenuRepository menuRepository;
  private final FoodRepository foodRepository;

  public List<MenuDto> getAllMenus() {
    return menuRepository.findAll().stream()
        .map(ProductMapper.INSTANCE::menuToDto)
        .collect(Collectors.toList());
  }

  public MenuDto getMenuById(long id) {
    return menuRepository.findByRestaurantId(id).map(ProductMapper.INSTANCE::menuToDto).orElse(null);
  }

  public MenuDto saveMenu(MenuDto menu) {
    return ProductMapper.INSTANCE.menuToDto(
        menuRepository.save(ProductMapper.INSTANCE.dtoToMenu(menu)));
  }

  public void deleteMenu(long id) {
    menuRepository.deleteByRestaurantOwnerId(id);
  }

  public FoodDto addFoodToMenu(long menuId, FoodDto foodDto) {
    Menu menu =
        menuRepository
            .findById(menuId)
            .orElseThrow(() -> new IllegalArgumentException("Menu not found"));
    Food food = ProductMapper.INSTANCE.dtoToFood(foodDto);
    food.setMenu(menu);
    return ProductMapper.INSTANCE.foodToDto(foodRepository.save(food));
  }
}
