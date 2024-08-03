package com.dish_dash.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.dishDash.common.Price;
import com.dishDash.common.dto.FoodDto;
import com.dishDash.common.dto.MenuDto;
import com.dishDash.common.enums.ErrorCode;
import com.dishDash.common.exception.CustomException;
import com.dish_dash.product.application.service.MenuService;
import com.dish_dash.product.domain.model.Category;
import com.dish_dash.product.domain.model.Food;
import com.dish_dash.product.domain.model.Menu;
import com.dish_dash.product.infrastructure.repository.CategoryRepository;
import com.dish_dash.product.infrastructure.repository.FoodRepository;
import com.dish_dash.product.infrastructure.repository.MenuRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MenuServiceIntegrationTest {

  @Autowired
  private MenuRepository menuRepository;
  @Autowired
  private FoodRepository foodRepository;
  @Autowired
  private CategoryRepository categoryRepository;
  @Autowired
  private MenuService menuService;

  private Menu menu;
  private Category category;

  @BeforeEach
  void setUp() {
    foodRepository.deleteAll();
    menuRepository.deleteAll();
    categoryRepository.deleteAll();

    foodRepository.flush();
    menuRepository.flush();
    categoryRepository.flush();

    menu = menuRepository.saveAndFlush(Menu.builder().restaurantId(1L).build());
    category = categoryRepository.saveAndFlush(
        Category.builder().name("CATEGORY_NAME").menu(menu).build());
  }

  @Test
  void getAllMenus_ShouldReturnAllMenus() {
    List<MenuDto> menus = menuService.getAllMenus();

    assertNotNull(menus, "Menus should not be null");
    assertEquals(1, menus.size(), "There should be exactly one menu item");
    assertEquals(menu.getId(), menus.get(0).getId(), "Menu ID should match");
    assertEquals(1L, menus.get(0).getRestaurantId(), "Restaurant ID should match");
  }

  @Test
  void getMenuById_ShouldReturnMenuDto_WhenMenuExists() {
    MenuDto menuDto = menuService.getMenuById(menu.getRestaurantId());

    assertNotNull(menuDto, "Menu should not be null");
    assertEquals(menu.getId(), menuDto.getId(), "Menu ID should match");
    assertEquals(1L, menuDto.getRestaurantId(), "Restaurant ID should match");
  }

  @Test
  void getMenuById_ShouldThrowException_WhenMenuDoesNotExist() {
    long nonExistingMenuId = 999L;

    CustomException exception = assertThrows(CustomException.class,
        () -> menuService.getMenuById(nonExistingMenuId));

    assertEquals(ErrorCode.NOT_FOUND_NO_CONTENT, exception.getErrorCode(),
        "Error code should be NOT_FOUND_NO_CONTENT");
    assertEquals("Menu not found for restaurant ID: " + nonExistingMenuId, exception.getMessage(),
        "Exception message should indicate the menu was not found");
  }

  @Test
  void saveMenu_ShouldSaveAndReturnMenuDto() {
    MenuDto newMenuDto = MenuDto.builder().restaurantId(2L).build();

    MenuDto savedMenuDto = menuService.saveMenu(newMenuDto);

    assertNotNull(savedMenuDto, "Saved menu should not be null");
    assertEquals(2L, savedMenuDto.getRestaurantId(), "Restaurant ID should be 2L");

    Menu savedMenu = menuRepository.findByRestaurantId(2L).orElse(null);
    assertNotNull(savedMenu, "Menu in repository should not be null");
    assertEquals(2L, savedMenu.getRestaurantId(), "Restaurant ID should match");
  }

  @Test
  void addFoodToMenu_ShouldAddFoodAndReturnFoodDto() {
    FoodDto foodDto = FoodDto.builder().name("FOOD_NAME").description("FOOD_DESCRIPTION")
        .price(Price.builder().amount(10).build()).categoryId(category.getId()).build();

    FoodDto savedFoodDto = menuService.addFoodToMenu(menu.getId(), foodDto);

    assertNotNull(savedFoodDto, "Saved food should not be null");
    assertEquals("FOOD_NAME", savedFoodDto.getName(), "Food name should be FOOD_NAME");
    assertEquals("FOOD_DESCRIPTION", savedFoodDto.getDescription(),
        "Food description should be FOOD_DESCRIPTION");

    Food savedFood = foodRepository.findById(savedFoodDto.getId()).orElse(null);
    assertNotNull(savedFood, "Food in repository should not be null");
    assertEquals("FOOD_NAME", savedFood.getName(), "Food name should match");
    assertEquals("FOOD_DESCRIPTION", savedFood.getDescription(), "Food description should match");
    assertEquals(menu.getId(), savedFood.getMenu().getId(), "Menu ID should match");
  }

  @Test
  void addFoodToMenu_ShouldThrowException_WhenMenuDoesNotExist() {
    FoodDto foodDto = FoodDto.builder().name("FOOD_NAME").description("FOOD_DESCRIPTION")
        .price(Price.builder().amount(10).build()).categoryId(category.getId()).build();

    long nonExistingMenuId = 999L;

    CustomException exception = assertThrows(CustomException.class,
        () -> menuService.addFoodToMenu(nonExistingMenuId, foodDto));

    assertEquals(ErrorCode.NOT_FOUND_NO_CONTENT, exception.getErrorCode(),
        "Error code should be NOT_FOUND_NO_CONTENT");
    assertEquals("Menu not found for ID: " + nonExistingMenuId, exception.getMessage(),
        "Exception message should indicate the menu was not found");
  }
}
