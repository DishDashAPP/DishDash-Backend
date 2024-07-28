package com.dish_dash.product.application.service;

import com.dishDash.common.dto.FoodDto;
import com.dishDash.common.dto.FoodViewDto;
import com.dish_dash.product.domain.mapper.ProductMapper;
import com.dish_dash.product.domain.model.Food;
import com.dish_dash.product.infrastructure.repository.FoodRepository;
import java.util.List;
import java.util.stream.Collectors;

import com.dish_dash.product.infrastructure.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodService {
  private final FoodRepository foodRepository;
  private final CategoryService categoryService;
  private final MenuRepository menuRepository;

  public List<FoodViewDto> getAllFoods() {
    return foodRepository.findAll().stream()
        .map(ProductMapper.INSTANCE::foodToViewDto)
        .collect(Collectors.toList());
  }

  public FoodViewDto getFoodById(long id) {
    return foodRepository.findById(id).map(ProductMapper.INSTANCE::foodToViewDto).orElse(null);
  }

  public FoodDto saveFood(FoodDto foodDto, long userId) {
    Food food = ProductMapper.INSTANCE.dtoToFood(foodDto);
    food.setCategory(categoryService.getReferenceCategory(foodDto.getCategoryId()));

    var menu = menuRepository.findByRestaurantId(userId);
    food.setMenu(menu.get());

    return ProductMapper.INSTANCE.foodToDto(foodRepository.save(food));
  }

  public void deleteFood(long id) {
    foodRepository.deleteById(id);
  }
}
