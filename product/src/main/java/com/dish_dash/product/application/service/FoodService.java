package com.dish_dash.product.application.service;

import com.dishDash.common.dto.FoodDto;
import com.dishDash.common.dto.FoodViewDto;
import com.dish_dash.product.domain.mapper.ProductMapper;
import com.dish_dash.product.domain.model.Food;
import com.dish_dash.product.infrastructure.repository.FoodRepository;
import com.dish_dash.product.infrastructure.repository.MenuRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FoodService {

  private final FoodRepository foodRepository;
  private final CategoryService categoryService;
  private final MenuRepository menuRepository;

  /**
   * Retrieves all food items and maps them to FoodViewDto.
   *
   * @return List of FoodViewDto representing all food items.
   */
  public List<FoodViewDto> getAllFoods() {
    return foodRepository.findAll().stream()
            .map(ProductMapper.INSTANCE::foodToViewDto)
            .collect(Collectors.toList());
  }

  /**
   * Retrieves a specific food item by its ID.
   *
   * @param id the unique identifier of the food item.
   * @return FoodViewDto representing the food item, or null if not found.
   */
  public FoodViewDto getFoodById(long id) {
    return foodRepository.findById(id).map(ProductMapper.INSTANCE::foodToViewDto).orElse(null);
  }

  /**
   * Saves a new food item to the database.
   *
   * @param foodDto the details of the food item to save.
   * @param userId the ID of the user associated with the food item.
   * @return FoodDto representing the saved food item.
   */
  @Transactional
  public FoodDto saveFood(FoodDto foodDto, Long userId) {
    Food food = ProductMapper.INSTANCE.dtoToFood(foodDto);
    food.setCategory(categoryService.getReferenceCategory(foodDto.getCategoryId()));

    var menu = menuRepository.findByRestaurantId(userId);
    if (menu.isPresent()) {
      food.setMenu(menu.get());
    } else {
      throw new IllegalArgumentException("Menu not found for the specified restaurant ID.");
    }

    return ProductMapper.INSTANCE.foodToDto(foodRepository.save(food));
  }

  /**
   * Deletes a food item by its ID.
   *
   * @param id the unique identifier of the food item to delete.
   */
  public void deleteFood(long id) {
    if (foodRepository.existsById(id)) {
      foodRepository.deleteById(id);
    } else {
      throw new IllegalArgumentException("Food item not found with the specified ID.");
    }
  }

  /**
   * Modifies an existing food item.
   *
   * @param foodDto the new details of the food item to update.
   * @param userId the ID of the user associated with the food item.
   * @return FoodDto representing the updated food item.
   */
  @Transactional
  public FoodDto modifyFood(long id, FoodDto foodDto, Long userId) {
    Food existingFood = foodRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Food item not found with the specified ID."));

    existingFood.setName(foodDto.getName());
    existingFood.setDescription(foodDto.getDescription());
    existingFood.setPrice(foodDto.getPrice());
    existingFood.setCategory(categoryService.getReferenceCategory(foodDto.getCategoryId()));

    var menu = menuRepository.findByRestaurantId(userId);
    if (menu.isPresent()) {
      existingFood.setMenu(menu.get());
    } else {
      throw new IllegalArgumentException("Menu not found for the specified restaurant ID.");
    }

    return ProductMapper.INSTANCE.foodToDto(foodRepository.save(existingFood));
  }
}
