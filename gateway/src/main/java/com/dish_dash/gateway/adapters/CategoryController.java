package com.dish_dash.gateway.adapters;

import com.dishDash.common.dto.CategoryCreationDto;
import com.dishDash.common.dto.CategoryViewDto;
import com.dishDash.common.feign.Product.CategoryApi;
import com.dish_dash.gateway.annotation.Authentication;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/category")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
  private final CategoryApi categoryApi;

  @GetMapping
  @Authentication(userId = "#userId")
  public List<CategoryViewDto> getAllCategories(Long userId) {
    log.info("getAllCategories, userId: {}", userId);
    return categoryApi.getAllCategories(userId);
  }

  @GetMapping("/{id}")
  @Authentication
  public CategoryViewDto getCategoryById(@PathVariable Long id) {
    return categoryApi.getCategoryById(id);
  }

  @PostMapping
  @Authentication(userId = "#userId")
  CategoryCreationDto createCategory(Long userId, @RequestBody CategoryCreationDto category) {
    return categoryApi.createCategory(userId, category);
  }

  @DeleteMapping("/{id}")
  @Authentication
  public void deleteCategory(@PathVariable Long id) {
    categoryApi.deleteCategory(id);
  }
}
