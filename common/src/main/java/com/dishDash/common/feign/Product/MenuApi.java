package com.dishDash.common.feign.Product;

import com.dishDash.common.dto.FoodDto;
import com.dishDash.common.dto.MenuDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-service", contextId = "menu-product-service", path = "/menu")
public interface MenuApi {
  @GetMapping()
  List<MenuDto> getAllMenus();

  @GetMapping("/{id}")
  MenuDto getMenuById(@PathVariable long id);

  @PostMapping()
  MenuDto createMenu(@RequestBody MenuDto menu);

  @DeleteMapping("/{id}")
  void deleteMenu(@PathVariable long id);

  @PostMapping("/{menuId}/foods")
  FoodDto addFoodToMenu(@PathVariable long menuId, @RequestBody FoodDto food);
}
