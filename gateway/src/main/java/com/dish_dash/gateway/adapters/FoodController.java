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
  public FoodDto createFood(Long userId, @RequestBody FoodDto foodDto) {
    return foodApi.createFood(userId, foodDto);
  }

  @PutMapping("/{id}")
  @Authentication(userId = "#userId")
  public FoodDto modifyFood(Long userId, @PathVariable long id, @RequestBody FoodDto foodDto) {
    // Ensure that the modifyFood method is correctly mapping parameters
    return foodApi.modifyFood(id, userId, foodDto);
  }

  @DeleteMapping("/{id}")
  @Authentication
  public void deleteFood(@PathVariable long id) {
    foodApi.deleteFood(id);
  }
}
