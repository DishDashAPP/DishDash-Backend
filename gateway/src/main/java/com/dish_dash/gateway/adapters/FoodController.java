package com.dish_dash.gateway.adapters;

import com.dishDash.common.dto.FoodDto;
import com.dishDash.common.dto.FoodViewDto;
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

  @GetMapping
  @Authentication
  public List<FoodViewDto> getAllFoods() {
    return foodApi.getAllFoods();
  }

  @GetMapping("/{id}")
  @Authentication
  public FoodViewDto getFoodById(@PathVariable long id) {
    return foodApi.getFoodById(id);
  }

  @PostMapping
  @Authentication(userId = "#userId")
  public FoodDto createFood(@RequestBody FoodDto foodDto, long userId) {
    return foodApi.createFood(userId, foodDto);
  }

  @DeleteMapping("/{id}")
  @Authentication
  public void deleteFood(@PathVariable long id) {
    foodApi.deleteFood(id);
  }
}
