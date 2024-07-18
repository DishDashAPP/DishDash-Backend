package com.dish_dash.gateway.adapters;

import com.dishDash.common.dto.MenuDto;
import com.dishDash.common.feign.Product.MenuApi;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/menu")
@RequiredArgsConstructor
public class MenuController {
  private final MenuApi menuApi;

  @GetMapping()
  public List<MenuDto> getAllMenus() {
    return menuApi.getAllMenus();
  }

  @GetMapping("/{id}")
  public MenuDto getMenuById(@PathVariable Long id) {
    return menuApi.getMenuById(id);
  }

  @PostMapping()
  public MenuDto createMenu(@RequestBody MenuDto menuDto) {
    return menuApi.createMenu(menuDto);
  }

  @DeleteMapping("/{id}")
  public void deleteMenu(@PathVariable Long id) {
    menuApi.deleteMenu(id);
  }
}
