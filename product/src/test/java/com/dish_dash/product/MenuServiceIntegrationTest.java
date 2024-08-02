package com.dish_dash.product;

import static org.junit.jupiter.api.Assertions.*;

import com.dishDash.common.Price;
import com.dishDash.common.dto.FoodDto;
import com.dishDash.common.dto.MenuDto;
import com.dish_dash.product.application.service.MenuService;
import com.dish_dash.product.domain.model.Food;
import com.dish_dash.product.domain.model.Menu;
import com.dish_dash.product.infrastructure.repository.FoodRepository;
import com.dish_dash.product.infrastructure.repository.MenuRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Disabled
public class MenuServiceIntegrationTest {

  @Autowired private MenuRepository menuRepository;

  @Autowired private FoodRepository foodRepository;

  @Autowired private MenuService menuService;

  private MenuDto menuDto;
  private FoodDto foodDto;

  @BeforeEach
  void setUp() {
    menuRepository.deleteAll();
    foodRepository.deleteAll();

    menuRepository.flush();
    foodRepository.flush();

    menuDto = MenuDto.builder().restaurantId(1L).build();

    foodDto =
        FoodDto.builder()
            .name("FOOD_NAME")
            .description("FOOD_DESCRIPTION")
            .price(Price.builder().amount(10).build())
            .build();
  }

  @Test
  void getAllMenus_ShouldReturnAllMenus() {
    Menu menu = Menu.builder().restaurantId(1L).build();
    menuRepository.saveAndFlush(menu);

    List<MenuDto> menus = menuService.getAllMenus();

    assertNotNull(menus, "Menus should not be null");
    assertEquals(1, menus.size(), "There should be exactly one menu");
  }

  @Test
  void getMenuById_ShouldReturnMenuDto_WhenMenuExists() {
    Menu menu = menuRepository.saveAndFlush(Menu.builder().restaurantId(1L).build());

    MenuDto retrievedMenu = menuService.getMenuById(menu.getId());

    assertNotNull(retrievedMenu, "Menu should not be null");
    assertEquals(menu.getId(), retrievedMenu.getId(), "Menu ID should match");
  }

  @Test
  void getMenuById_ShouldReturnNull_WhenMenuDoesNotExist() {
    MenuDto menuDto = menuService.getMenuById(1L);

    assertNull(menuDto, "Menu should be null for non-existing menu");
  }

  @Test
  void saveMenu_ShouldSaveAndReturnMenuDto() {
    MenuDto savedMenuDto = menuService.saveMenu(menuDto);

    assertNotNull(savedMenuDto, "Saved menu should not be null");

    Menu savedMenu = menuRepository.findById(savedMenuDto.getId()).orElse(null);
    assertNotNull(savedMenu, "Menu in repository should not be null");
  }

  @Test
  void deleteMenu_ShouldRemoveMenu() {
    Menu menu = menuRepository.saveAndFlush(Menu.builder().restaurantId(1L).build());

    menuService.deleteMenu(menu.getId());

    Menu deletedMenu = menuRepository.findById(menu.getId()).orElse(null);

    assertNull(deletedMenu, "Menu should be null after deletion");
  }

  @Test
  void addFoodToMenu_ShouldAddFoodAndReturnFoodDto() {
    Menu menu = menuRepository.saveAndFlush(Menu.builder().restaurantId(1L).build());

    FoodDto savedFoodDto = menuService.addFoodToMenu(menu.getId(), foodDto);

    assertNotNull(savedFoodDto, "Saved food should not be null");
    assertEquals("FOOD_NAME", savedFoodDto.getName(), "Food name should be FOOD_NAME");

    Food savedFood = foodRepository.findById(1L).orElse(null);
    assertNotNull(savedFood, "Food in repository should not be null");
    assertEquals(menu.getId(), savedFood.getMenu().getId(), "Menu ID should match");
  }
}
