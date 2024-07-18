package com.dish_dash.product.application.service;

import com.dishDash.common.dto.FoodDto;
import com.dish_dash.product.domain.mapper.ProductMapper;
import com.dish_dash.product.infrastructure.repository.FoodRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodService {

  private final FoodRepository foodRepository;

  public List<FoodDto> getAllFoods() {
    return foodRepository.findAll().stream()
        .map(ProductMapper.INSTANCE::foodToDto)
        .collect(Collectors.toList());
  }

  public FoodDto getFoodById(Long id) {
    return foodRepository.findById(id).map(ProductMapper.INSTANCE::foodToDto).orElse(null);
  }

  public FoodDto saveFood(FoodDto food) {
    return ProductMapper.INSTANCE.foodToDto(
        foodRepository.save(ProductMapper.INSTANCE.dtoToFood(food)));
  }

  public void deleteFood(Long id) {
    foodRepository.deleteById(id);
  }
}
