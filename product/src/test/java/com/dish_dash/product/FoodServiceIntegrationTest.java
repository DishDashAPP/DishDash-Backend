package com.dish_dash.product;

import com.dishDash.common.Price;
import com.dishDash.common.dto.FoodDto;
import com.dishDash.common.dto.FoodViewDto;
import com.dish_dash.product.application.service.FoodService;
import com.dish_dash.product.domain.model.Category;
import com.dish_dash.product.domain.model.Food;
import com.dish_dash.product.domain.model.Menu;
import com.dish_dash.product.infrastructure.repository.CategoryRepository;
import com.dish_dash.product.infrastructure.repository.FoodRepository;
import com.dish_dash.product.infrastructure.repository.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FoodServiceIntegrationTest {

  @Autowired private FoodRepository foodRepository;

  @Autowired private MenuRepository menuRepository;

  @Autowired private CategoryRepository categoryRepository;

  @Autowired private FoodService foodService;

  private FoodDto foodDto;

  private Category category;
  private Menu menu;

  @BeforeEach
  void setUp() {
    foodRepository.deleteAll();
    menuRepository.deleteAll();
    categoryRepository.deleteAll();

    foodRepository.flush();
    menuRepository.flush();
    categoryRepository.flush();

    category = Category.builder().name("CATEGORY_NAME").build();
    category = categoryRepository.saveAndFlush(category);

    menu = Menu.builder().restaurantId(1L).build();
    menu = menuRepository.saveAndFlush(menu);

    foodDto =
        FoodDto.builder()
            .name("FOOD_NAME")
            .description("FOOD_DESCRIPTION")
            .price(Price.builder().amount(10).build())
            .categoryId(category.getId())
            .build();
  }

  @Test
  void getAllFoods_ShouldReturnAllFoods() {
    Food food =
        Food.builder()
            .name("FOOD_NAME")
            .description("FOOD_DESCRIPTION")
            .price(Price.builder().amount(10).build())
            .category(category)
            .menu(menu)
            .build();
    foodRepository.saveAndFlush(food);

    List<FoodViewDto> foods = foodService.getAllFoods();

    assertNotNull(foods, "Foods should not be null");
    assertEquals(1, foods.size(), "There should be exactly one food item");
    assertEquals("FOOD_NAME", foods.get(0).getName(), "Food name should be FOOD_NAME");
    assertEquals(
        "FOOD_DESCRIPTION",
        foods.get(0).getDescription(),
        "Food description should be FOOD_DESCRIPTION");
  }

  @Test
  void getFoodById_ShouldReturnFoodViewDto_WhenFoodExists() {
    Food food =
        Food.builder()
            .name("FOOD_NAME")
            .description("FOOD_DESCRIPTION")
            .price(Price.builder().amount(10).build())
            .category(category)
            .menu(menu)
            .build();
    food = foodRepository.saveAndFlush(food);

    FoodViewDto foodViewDto = foodService.getFoodById(food.getId());

    assertNotNull(foodViewDto, "Food should not be null");
    assertEquals(food.getId(), foodViewDto.getId(), "Food ID should match");
    assertEquals("FOOD_NAME", foodViewDto.getName(), "Food name should be FOOD_NAME");
    assertEquals(
        "FOOD_DESCRIPTION",
        foodViewDto.getDescription(),
        "Food description should be FOOD_DESCRIPTION");
  }

  @Test
  void getFoodById_ShouldReturnNull_WhenFoodDoesNotExist() {
    FoodViewDto foodViewDto = foodService.getFoodById(1L);

    assertNull(foodViewDto, "Food should be null for non-existing food item");
  }

  @Test
  void saveFood_ShouldSaveAndReturnFoodDto() {
    FoodDto savedFoodDto = foodService.saveFood(foodDto, 1L);

    assertNotNull(savedFoodDto, "Saved food should not be null");
    assertEquals("FOOD_NAME", savedFoodDto.getName(), "Food name should be FOOD_NAME");
    assertEquals(
        "FOOD_DESCRIPTION",
        savedFoodDto.getDescription(),
        "Food description should be FOOD_DESCRIPTION");

    Food savedFood = foodRepository.findById(1L).orElse(null);
    assertNotNull(savedFood, "Food in repository should not be null");
    assertEquals("FOOD_NAME", savedFood.getName(), "Food name should match");
    assertEquals("FOOD_DESCRIPTION", savedFood.getDescription(), "Food description should match");
    assertEquals(category.getId(), savedFood.getCategory().getId(), "Category ID should match");
  }

  @Test
  void deleteFood_ShouldRemoveFood() {
    Food food =
        Food.builder()
            .name("FOOD_NAME")
            .description("FOOD_DESCRIPTION")
            .price(Price.builder().amount(10).build())
            .category(category)
            .menu(menu)
            .build();
    food = foodRepository.saveAndFlush(food);

    foodService.deleteFood(food.getId());

    Food deletedFood = foodRepository.findById(food.getId()).orElse(null);

    assertNull(deletedFood, "Food should be null after deletion");
  }
}
