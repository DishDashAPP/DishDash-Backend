package com.dishDash.common.feign.Product;

import com.dishDash.common.dto.FoodDto;
import java.util.List;

import com.dishDash.common.dto.FoodViewDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-service", contextId = "food-product-service", path = "/food")
public interface FoodApi {

  @GetMapping()
  List<FoodViewDto> getAllFoods();

  @GetMapping("/{id}")
  FoodViewDto getFoodById(@PathVariable Long id);

  @PostMapping()
  FoodDto createFood(@RequestBody FoodDto foodDto);

  @DeleteMapping("/{id}")
  void deleteFood(@PathVariable Long id);
}
