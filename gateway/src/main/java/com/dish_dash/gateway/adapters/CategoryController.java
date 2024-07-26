package com.dish_dash.gateway.adapters;

import com.dishDash.common.dto.CategoryCreationDto;
import com.dishDash.common.dto.CategoryViewDto;
import com.dishDash.common.feign.Product.CategoryApi;
import com.dish_dash.gateway.annotation.Authentication;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/category")
@RequiredArgsConstructor
public class CategoryController {
  private final CategoryApi categoryApi;

  @GetMapping
  @Authentication
  public List<CategoryViewDto> getAllCategories() {
    return categoryApi.getAllCategories();
  }

  @GetMapping("/{id}")
  @Authentication
  public CategoryViewDto getCategoryById(@PathVariable long id) {
    return categoryApi.getCategoryById(id);
  }

  @PostMapping
  @Authentication
  public CategoryCreationDto createCategory(@RequestBody CategoryCreationDto category) {
    return categoryApi.createCategory(category);
  }

  @DeleteMapping("/{id}")
  @Authentication
  public void deleteCategory(@PathVariable long id) {
    categoryApi.deleteCategory(id);
  }
}
