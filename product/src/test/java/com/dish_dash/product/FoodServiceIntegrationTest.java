package com.dish_dash.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.dishDash.common.Price;
import com.dishDash.common.dto.FoodDto;
import com.dishDash.common.dto.FoodViewDto;
import com.dishDash.common.enums.ErrorCode;
import com.dishDash.common.exception.CustomException;
import com.dish_dash.product.application.service.FoodService;
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
public class FoodServiceIntegrationTest {

  @Autowired
  private FoodRepository foodRepository;

  @Autowired
  private MenuRepository menuRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private FoodService foodService;

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

    menu = menuRepository.saveAndFlush(Menu.builder().restaurantId(1L).build());
    category = categoryRepository.saveAndFlush(
        Category.builder().name("CATEGORY_NAME").menu(menu).build());

    foodDto = FoodDto.builder().name("FOOD_NAME").description("FOOD_DESCRIPTION")
        .price(Price.builder().amount(10).build()).categoryId(category.getId()).build();
  }

  @Test
  void getAllFoods_ShouldReturnAllFoods() {
    Food food = foodRepository.saveAndFlush(
        Food.builder().name("FOOD_NAME").description("FOOD_DESCRIPTION")
            .price(Price.builder().amount(10).build()).category(category).menu(menu).build());

    List<FoodViewDto> foods = foodService.getAllFoods(menu.getRestaurantId());

    assertNotNull(foods, "Foods should not be null");
    assertEquals(1, foods.size(), "There should be exactly one food item");
    assertEquals(food.getName(), foods.get(0).getName(), "Food name should be FOOD_NAME");
    assertEquals(food.getDescription(), foods.get(0).getDescription(),
        "Food description should be " + "FOOD_DESCRIPTION");
  }

  @Test
  void getFoodById_ShouldReturnFoodViewDto_WhenFoodExists() {
    Food food = foodRepository.saveAndFlush(
        Food.builder().name("FOOD_NAME").description("FOOD_DESCRIPTION")
            .price(Price.builder().amount(10).build()).category(category).menu(menu).build());

    FoodViewDto foodViewDto = foodService.getFoodById(food.getId());

    assertNotNull(foodViewDto, "Food should not be null");
    assertEquals(food.getId(), foodViewDto.getId(), "Food ID should match");
    assertEquals("FOOD_NAME", foodViewDto.getName(), "Food name should be FOOD_NAME");
    assertEquals("FOOD_DESCRIPTION", foodViewDto.getDescription(),
        "Food description should be FOOD_DESCRIPTION");
  }

  @Test
  void getFoodById_ShouldThrowException_WhenFoodDoesNotExist() {
    long nonExistingFoodId = 999L;

    CustomException exception = assertThrows(CustomException.class, () -> {
      foodService.getFoodById(nonExistingFoodId);
    });

    assertEquals(ErrorCode.NOT_FOUND, exception.getErrorCode(), "Error code should be NOT_FOUND");
    assertEquals("Food not found for ID: " + nonExistingFoodId, exception.getMessage(),
        "Exception message should indicate the food was not found");
  }

  @Test
  void saveFood_ShouldSaveAndReturnFoodDto() {
    FoodDto savedFoodDto = foodService.saveFood(foodDto, menu.getRestaurantId());

    assertNotNull(savedFoodDto, "Saved food should not be null");
    assertEquals("FOOD_NAME", savedFoodDto.getName(), "Food name should be FOOD_NAME");
    assertEquals("FOOD_DESCRIPTION", savedFoodDto.getDescription(),
        "Food description should be FOOD_DESCRIPTION");

    Food savedFood = foodRepository.findById(savedFoodDto.getId()).orElse(null);
    assertNotNull(savedFood, "Food in repository should not be null");
    assertEquals("FOOD_NAME", savedFood.getName(), "Food name should match");
    assertEquals("FOOD_DESCRIPTION", savedFood.getDescription(), "Food description should match");
    assertEquals(category.getId(), savedFood.getCategory().getId(), "Category ID should match");
  }

  @Test
  void deleteFood_ShouldRemoveFood() {
    Food food = foodRepository.saveAndFlush(
        Food.builder().name("FOOD_NAME").description("FOOD_DESCRIPTION")
            .price(Price.builder().amount(10).build()).category(category).menu(menu).build());

    foodService.deleteFood(food.getId());

    Food deletedFood = foodRepository.findById(food.getId()).orElse(null);
    assertNull(deletedFood, "Food should be null after deletion");
  }

  @Test
  void modifyFood_ShouldUpdateAndReturnUpdatedFoodDto() {
    Food food = foodRepository.saveAndFlush(
        Food.builder().name("ORIGINAL_NAME").description("ORIGINAL_DESCRIPTION")
            .price(Price.builder().amount(10).build()).category(category).menu(menu).build());

    FoodDto updateFoodDto = FoodDto.builder().name("UPDATED_NAME")
        .description("UPDATED_DESCRIPTION").price(Price.builder().amount(15).build())
        .categoryId(category.getId()).build();

    FoodDto updatedFoodDto = foodService.modifyFood(food.getId(), updateFoodDto,
        menu.getRestaurantId());

    assertNotNull(updatedFoodDto, "Updated food should not be null");
    assertEquals("UPDATED_NAME", updatedFoodDto.getName(), "Food name should be UPDATED_NAME");
    assertEquals("UPDATED_DESCRIPTION", updatedFoodDto.getDescription(),
        "Food description should be UPDATED_DESCRIPTION");
    assertEquals(15, updatedFoodDto.getPrice().getAmount(), "Food price should be updated to 15");

    Food updatedFood = foodRepository.findById(updatedFoodDto.getId()).orElse(null);
    assertNotNull(updatedFood, "Food in repository should not be null");
    assertEquals("UPDATED_NAME", updatedFood.getName(), "Food name should match");
    assertEquals("UPDATED_DESCRIPTION", updatedFood.getDescription(),
        "Food description should match");
    assertEquals(15, updatedFood.getPrice().getAmount(), "Food price should match");
    assertEquals(category.getId(), updatedFood.getCategory().getId(), "Category ID should match");
  }
}
