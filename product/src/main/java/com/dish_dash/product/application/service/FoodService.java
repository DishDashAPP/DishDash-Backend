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

  public List<FoodViewDto> getAllFoods(long userId) {
    return foodRepository.findByMenu_RestaurantId(userId).stream()
        .map(ProductMapper.INSTANCE::foodToViewDto)
        .collect(Collectors.toList());
  }

  public FoodViewDto getFoodById(long id) {
    return foodRepository.findById(id).map(ProductMapper.INSTANCE::foodToViewDto).orElse(null);
  }

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

  public void deleteFood(long id) {
    if (foodRepository.existsById(id)) {
      foodRepository.deleteById(id);
    } else {
      throw new IllegalArgumentException("Food item not found with the specified ID.");
    }
  }

  @Transactional
  public FoodDto modifyFood(long id, FoodDto foodDto, Long userId) {
    Food existingFood =
        foodRepository
            .findById(id)
            .orElseThrow(
                () -> new IllegalArgumentException("Food item not found with the specified ID."));

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
