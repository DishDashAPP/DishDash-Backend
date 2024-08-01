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
  @GetMapping
  public List<FoodViewDto> getAllFoods() {
    return foodService.getAllFoods();
  }

  @Override
  @GetMapping("/{id}")
  public FoodViewDto getFoodById(@PathVariable long id) {
    return foodService.getFoodById(id);
  }

  @Override
  @PostMapping
  public FoodDto createFood(@RequestParam Long userId, @RequestBody FoodDto foodDto) {
    return foodService.saveFood(foodDto, userId);
  }

  @Override
  @PutMapping("/{id}")
  public FoodDto modifyFood(@RequestParam Long userId, @PathVariable long id, @RequestBody FoodDto foodDto) {
    return foodService.modifyFood(id, foodDto, userId);
  }

  @Override
  @DeleteMapping("/{id}")
  public void deleteFood(@PathVariable long id) {
    foodService.deleteFood(id);
  }
}
