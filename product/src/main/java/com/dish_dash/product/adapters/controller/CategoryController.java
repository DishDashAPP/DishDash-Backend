package com.dish_dash.product.adapters.controller;

import com.dishDash.common.dto.CategoryCreationDto;
import com.dishDash.common.dto.CategoryViewDto;
import com.dishDash.common.feign.Product.CategoryApi;
import com.dish_dash.product.application.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController implements CategoryApi {
  private final CategoryService categoryService;

  @Override
  public List<CategoryViewDto> getAllCategories() {
    return categoryService.getAllCategories();
  }

  @Override
  public CategoryViewDto getCategoryById(Long id) {
    return categoryService.getCategoryById(id);
  }

  @Override
  public CategoryCreationDto createCategory(CategoryCreationDto category) {
    return categoryService.saveCategory(category);
  }

  @Override
  public void deleteCategory(Long id) {
    categoryService.deleteCategory(id);
  }
}
