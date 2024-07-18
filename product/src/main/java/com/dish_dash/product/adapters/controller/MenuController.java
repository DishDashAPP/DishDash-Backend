package com.dish_dash.product.adapters.controller;

import com.dishDash.common.dto.FoodDto;
import com.dishDash.common.dto.MenuDto;
import com.dishDash.common.feign.Product.MenuApi;
import com.dish_dash.product.application.service.MenuService;
import com.dish_dash.product.domain.mapper.ProductMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController implements MenuApi {
  private final MenuService menuService;

  @Override
  public List<MenuDto> getAllMenus() {
    return menuService.getAllMenus();
  }

  @Override
  public MenuDto getMenuById(Long id) {
    return menuService.getMenuById(id);
  }

  @Override
  public MenuDto createMenu(MenuDto menu) {
    return menuService.saveMenu(menu);
  }

  @Override
  public void deleteMenu(Long id) {
    menuService.deleteMenu(id);
  }

  @Override
  public FoodDto addFoodToMenu(Long menuId, FoodDto food) {
    return menuService.addFoodToMenu(menuId, ProductMapper.INSTANCE.dtoToFood(food));
  }
}
