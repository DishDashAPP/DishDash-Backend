package com.dish_dash.product.adapters.controller;

import com.dishDash.common.dto.FoodDto;
import com.dishDash.common.feign.Product.FoodApi;
import com.dish_dash.product.application.service.FoodService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/food")
@RequiredArgsConstructor
public class FoodController implements FoodApi {
  private final FoodService foodService;

  @Override
  public List<FoodDto> getAllFoods() {
    return foodService.getAllFoods();
  }

  @Override
  public FoodDto getFoodById(Long id) {
    return foodService.getFoodById(id);
  }

  @Override
  public FoodDto createFood(FoodDto foodDto) {
    return foodService.saveFood(foodDto);
  }

  @Override
  public void deleteFood(Long id) {
    foodService.deleteFood(id);
  }
}
