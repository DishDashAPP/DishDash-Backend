package com.dish_dash.product.adapters.controller;

import com.dishDash.common.dto.FoodDto;
import com.dishDash.common.dto.FoodViewDto;
import com.dishDash.common.feign.Product.FoodApi;
import com.dish_dash.product.application.service.FoodService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/food")
public class FoodController implements FoodApi {
  private final FoodService foodService;

  @Override
  public List<FoodViewDto> getAllFoods() {
    return foodService.getAllFoods();
  }

  @Override
  public FoodViewDto getFoodById(long id) {
    return foodService.getFoodById(id);
  }

  @Override
  public FoodDto createFood(Long userId, FoodDto foodDto) {
    return foodService.saveFood(foodDto, userId);
  }

  @Override
  public void deleteFood(long id) {
    foodService.deleteFood(id);
  }
}
