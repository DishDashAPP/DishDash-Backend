package com.dish_dash.product.application.service;

import com.dishDash.common.dto.FoodDto;
import com.dishDash.common.dto.FoodViewDto;
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
public class FoodService {

  private final FoodRepository foodRepository;
  private final CategoryService categoryService;
  private final MenuRepository menuRepository;

  public List<FoodViewDto> getAllFoods(long userId) {
    log.info("Retrieving all foods for restaurant ID: {}", userId);
    return foodRepository.findByMenu_RestaurantId(userId).stream()
        .map(ProductMapper.INSTANCE::foodToViewDto).toList();
  }

  public FoodViewDto getFoodById(long id) {
    log.info("Retrieving food by ID: {}", id);
    return foodRepository.findById(id).map(ProductMapper.INSTANCE::foodToViewDto)
        .orElseThrow(() -> {
          log.error("Food with ID: {} not found", id);
          return new CustomException(ErrorCode.NOT_FOUND, "Food not found for ID: " + id);
        });
  }

  @Transactional
  public FoodDto saveFood(FoodDto foodDto, Long userId) {
    Food food = ProductMapper.INSTANCE.dtoToFood(foodDto);
    food.setCategory(categoryService.getReferenceCategory(foodDto.getCategoryId()));

    Menu menu = menuRepository.findByRestaurantId(userId).orElseThrow(() -> {
      log.error("Menu not found for restaurant ID: {}", userId);
      return new CustomException(ErrorCode.NOT_FOUND,
          "Menu not found for restaurant ID: " + userId);
    });
    food.setMenu(menu);

    Food savedFood = foodRepository.save(food);
    log.info("Food saved with ID: {}", savedFood.getId());
    return ProductMapper.INSTANCE.foodToDto(savedFood);
  }

  public void deleteFood(long id) {
    log.info("Deleting food with ID: {}", id);

    if (!foodRepository.existsById(id)) {
      log.error("Attempt to delete non-existing food with ID: {}", id);
      throw new CustomException(ErrorCode.NOT_FOUND, "Food item not found with the specified ID.");
    }

    foodRepository.deleteById(id);
    log.info("Food with ID: {} deleted successfully", id);
  }

  @Transactional
  public FoodDto modifyFood(long id, FoodDto foodDto, Long userId) {
    log.info("Modifying food with ID: {} for restaurant ID: {}", id, userId);
    Food existingFood = foodRepository.findById(id).orElseThrow(() -> {
      log.error("Food with ID: {} not found", id);
      return new CustomException(ErrorCode.NOT_FOUND, "Food item not found with the specified ID.");
    });

    updateFoodDetails(existingFood, foodDto, userId);

    Food updatedFood = foodRepository.save(existingFood);
    log.info("Food modified with ID: {}", updatedFood.getId());
    return ProductMapper.INSTANCE.foodToDto(updatedFood);
  }

  private void updateFoodDetails(Food food, FoodDto foodDto, Long userId) {
    food.setName(foodDto.getName());
    food.setDescription(foodDto.getDescription());
    food.setPrice(foodDto.getPrice());
    food.setCategory(categoryService.getReferenceCategory(foodDto.getCategoryId()));

    Menu menu = menuRepository.findByRestaurantId(userId).orElseThrow(() -> {
      log.error("Menu not found for restaurant ID: {}", userId);
      return new CustomException(ErrorCode.NOT_FOUND,
          "Menu not found for restaurant ID: " + userId);
    });
    food.setMenu(menu);
  }
}
