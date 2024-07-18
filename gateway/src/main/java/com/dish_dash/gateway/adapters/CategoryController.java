package com.dish_dash.gateway.adapters;

import com.dishDash.common.dto.CategoryDto;
import com.dishDash.common.feign.Product.CategoryApi;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/category")
@RequiredArgsConstructor
public class CategoryController {
  private final CategoryApi categoryApi;

  @GetMapping()
  public List<CategoryDto> getAllCategories() {
    return categoryApi.getAllCategories();
  }

  @GetMapping("/{id}")
  public CategoryDto getCategoryById(@PathVariable Long id) {
    return categoryApi.getCategoryById(id);
  }

  @PostMapping()
  public CategoryDto createCategory(@RequestBody CategoryDto category) {
    return categoryApi.createCategory(category);
  }

  @DeleteMapping("/{id}")
  public void deleteCategory(@PathVariable Long id) {
    categoryApi.deleteCategory(id);
  }
}
