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
  private final ProductMapper productMapper;

  public List<MenuDto> getAllMenus() {
    return menuRepository.findAll().stream()
        .map(ProductMapper.INSTANCE::menuToDto)
        .collect(Collectors.toList());
  }

  public MenuDto getMenuById(Long id) {
    return menuRepository.findById(id).map(ProductMapper.INSTANCE::menuToDto).orElse(null);
  }

  public MenuDto saveMenu(MenuDto menu) {
    return ProductMapper.INSTANCE.menuToDto(menuRepository.save(productMapper.dtoToMenu(menu)));
  }

  public void deleteMenu(Long id) {
    menuRepository.deleteById(id);
  }

  public FoodDto addFoodToMenu(Long menuId, Food food) {
    Menu menu =
        menuRepository
            .findById(menuId)
            .orElseThrow(() -> new IllegalArgumentException("Menu not found"));
    food.setMenu(menu);
    return ProductMapper.INSTANCE.foodToDto(foodRepository.save(food));
  }
}
