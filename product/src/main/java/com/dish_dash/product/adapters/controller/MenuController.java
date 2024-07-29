package com.dish_dash.product.adapters.controller;

import com.dishDash.common.dto.FoodDto;
import com.dishDash.common.dto.MenuDto;
import com.dishDash.common.feign.Product.MenuApi;
import com.dish_dash.product.application.service.MenuService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController implements MenuApi {
  private final MenuService menuService;

  @Override
  public List<MenuDto> getAllMenus() {
    return menuService.getAllMenus();
  }

  @Override
  public MenuDto getMenuById(long id) {
    return menuService.getMenuById(id);
  }

  @Override
  public MenuDto createMenu(MenuDto menu) {
    return menuService.saveMenu(menu);
  }

  @Override
  public void deleteMenu(long id) {
    menuService.deleteMenu(id);
  }

  @Override
  public FoodDto addFoodToMenu(long menuId, FoodDto foodDto) {
    return menuService.addFoodToMenu(menuId, foodDto);
  }
}
