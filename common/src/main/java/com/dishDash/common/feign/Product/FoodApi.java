package com.dishDash.common.feign.Product;

import com.dishDash.common.dto.FoodDto;
import com.dishDash.common.dto.FoodViewDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-service", contextId = "food-product-service", path = "/food")
public interface FoodApi {

  @GetMapping
  List<FoodViewDto>   getAllFoods(@RequestParam long userId);

  @GetMapping("/{id}")
  FoodViewDto getFoodById(@PathVariable long id);

  @PostMapping
  FoodDto createFood(@RequestParam Long userId, @RequestBody FoodDto foodDto);

  @PutMapping("/{id}")
  FoodDto modifyFood(
      @RequestParam Long userId, @PathVariable long id, @RequestBody FoodDto foodDto);

  @DeleteMapping("/{id}")
  void deleteFood(@PathVariable long id);
}
