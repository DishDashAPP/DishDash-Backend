package com.dish_dash.product.application.service;

import com.dishDash.common.dto.FoodDto;
import com.dishDash.common.dto.MenuDto;
import com.dishDash.common.enums.ErrorCode;
import com.dishDash.common.exception.CustomException;
import com.dish_dash.product.domain.mapper.ProductMapper;
import com.dish_dash.product.domain.model.Food;
import com.dish_dash.product.domain.model.Menu;
import com.dish_dash.product.infrastructure.repository.FoodRepository;
import com.dish_dash.product.infrastructure.repository.MenuRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuService {

  private final MenuRepository menuRepository;
  private final FoodRepository foodRepository;

  @Transactional(readOnly = true)
  public List<MenuDto> getAllMenus() {
    log.info("Retrieving all menus");
    return menuRepository.findAll().stream().map(ProductMapper.INSTANCE::menuToDto).toList();
  }

  @Transactional(readOnly = true)
  public MenuDto getMenuById(long id) {
    log.info("Retrieving menu by restaurant ID: {}", id);
    Menu menu = menuRepository.findByRestaurantId(id).orElseThrow(() -> {
      log.error("Menu not found for restaurant ID: {}", id);
      return new CustomException(ErrorCode.NOT_FOUND_NO_CONTENT,
          "Menu not found for restaurant ID: " + id);
    });

    MenuDto menuDto = ProductMapper.INSTANCE.menuToDto(menu);
    menuDto.setFoods(menu.getFoodList().stream().map(food -> {
      FoodDto dto = ProductMapper.INSTANCE.foodToDto(food);
      dto.setCategoryId(food.getCategory().getId());
      return dto;
    }).toList());
    menuDto.setCategories(
        menu.getCategories().stream().map(ProductMapper.INSTANCE::categoryToViewDto).toList());
    return menuDto;
  }

  public MenuDto saveMenu(MenuDto menu) {
    return ProductMapper.INSTANCE.menuToDto(
        menuRepository.save(ProductMapper.INSTANCE.dtoToMenu(menu)));
  }

  public void deleteMenu(long id) {
    log.info("Deleting menu for restaurant owner ID: {}", id);
    if (!menuRepository.existsByRestaurantId(id)) {
      log.error("Attempt to delete non-existing menu for restaurant owner ID: {}", id);
      throw new CustomException(ErrorCode.NOT_FOUND,
          "Menu not found for restaurant owner ID: " + id);
    }
    menuRepository.deleteByRestaurantOwnerId(id);
    log.info("Menu deleted successfully for restaurant owner ID: {}", id);
  }

  public FoodDto addFoodToMenu(long menuId, FoodDto foodDto) {
    log.info("Adding food to menu ID: {}", menuId);
    Menu menu = menuRepository.findById(menuId).orElseThrow(() -> {
      log.error("Menu with ID: {} not found", menuId);
      return new CustomException(ErrorCode.NOT_FOUND_NO_CONTENT,
          "Menu not found for ID: " + menuId);
    });

    Food food = ProductMapper.INSTANCE.dtoToFood(foodDto);
    food.setMenu(menu);
    Food savedFood = foodRepository.save(food);
    log.info("Food added to menu ID: {} with food ID: {}", menuId, savedFood.getId());
    return ProductMapper.INSTANCE.foodToDto(savedFood);
  }
}
