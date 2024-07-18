package com.dish_dash.gateway.adapters;

import com.dishDash.common.dto.FoodDto;
import com.dishDash.common.feign.Product.FoodApi;
import com.dish_dash.gateway.annotation.Authentication;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/food")
@RequiredArgsConstructor
public class FoodController {
  private final FoodApi foodApi;

  @GetMapping()
  @Authentication
  List<FoodDto> getAllFoods() {
    return foodApi.getAllFoods();
  }

  @GetMapping("/{id}")
  @Authentication
  FoodDto getFoodById(@PathVariable Long id) {
    return foodApi.getFoodById(id);
  }

  @PostMapping()
  @Authentication
  FoodDto createFood(@RequestBody FoodDto foodDto) {
    return foodApi.createFood(foodDto);
  }

  @DeleteMapping("/{id}")
  @Authentication
  void deleteFood(@PathVariable Long id) {
    foodApi.deleteFood(id);
  }
}
