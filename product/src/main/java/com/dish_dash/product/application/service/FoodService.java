package com.dish_dash.product.application.service;

import com.dish_dash.product.domain.model.Food;
import com.dish_dash.product.infrastructure.repository.FoodRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodService {

  private final FoodRepository foodRepository;

  public List<Food> getAllFoods() {
    return foodRepository.findAll();
  }

  public Food getFoodById(Long id) {
    return foodRepository.findById(id).orElse(null);
  }

  public Food saveFood(Food food) {
    return foodRepository.save(food);
  }

  public void deleteFood(Long id) {
    foodRepository.deleteById(id);
  }
}
