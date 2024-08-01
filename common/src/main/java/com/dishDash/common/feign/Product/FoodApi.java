package com.dishDash.common.feign.Product;

import com.dishDash.common.dto.FoodDto;
import com.dishDash.common.dto.FoodViewDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-service", contextId = "food-product-service", path = "/food")
public interface FoodApi {

  /**
   * Retrieves all food items.
   *
   * @return a list of FoodViewDto objects representing all food items.
   */
  @GetMapping
  List<FoodViewDto> getAllFoods();

  /**
   * Retrieves a specific food item by its ID.
   *
   * @param id the unique identifier of the food item.
   * @return a FoodViewDto object representing the food item.
   */
  @GetMapping("/{id}")
  FoodViewDto getFoodById(@PathVariable long id);

  /**
   *
   * Creates a new food item.
   *
   * @param userId the ID of the user creating the food item.
   * @param foodDto the details of the food item to be created.
   * @return a FoodDto object representing the newly created food item.
   */
  @PostMapping
  FoodDto createFood(@RequestParam Long userId, @RequestBody FoodDto foodDto);

  /**
   * Modifies an existing food item.
   *
   * @param id the unique identifier of the food item to modify.
   * @param userId the ID of the user modifying the food item.
   * @param foodDto the new details of the food item.
   * @return a FoodDto object representing the updated food item.
   */
  @PutMapping("/{id}")
  FoodDto modifyFood(@RequestParam Long userId, @PathVariable long id, @RequestBody FoodDto foodDto);

  /**
   * Deletes a specific food item by its ID.
   *
   * @param id the unique identifier of the food item to delete.
   */
  @DeleteMapping("/{id}")
  void deleteFood(@PathVariable long id);
}
