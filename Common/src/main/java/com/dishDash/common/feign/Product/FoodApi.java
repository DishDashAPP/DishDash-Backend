package com.dishDash.common.feign.Product;

import com.dishDash.common.dto.FoodDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-service", qualifiers = "food-product-service")
@RequestMapping("/foods")
public interface FoodApi {

  @GetMapping()
  List<FoodDto> getAllFoods();

  @GetMapping("/{id}")
  FoodDto getFoodById(@PathVariable Long id);

  @PostMapping()
  FoodDto createFood(@RequestBody FoodDto foodDto);

  @DeleteMapping("/{id}")
  void deleteFood(@PathVariable Long id);
}
