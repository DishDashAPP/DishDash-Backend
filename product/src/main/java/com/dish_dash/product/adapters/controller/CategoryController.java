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
  public List<CategoryViewDto> getAllCategories(Long userId) {
    return categoryService.getAllCategories(userId);
  }

  @Override
  public CategoryViewDto getCategoryById(long id) {
    return categoryService.getCategoryById(id);
  }

  @Override
  public CategoryCreationDto createCategory(long userId, CategoryCreationDto category) {
    return categoryService.saveCategory(category, userId);
  }

  @Override
  public void deleteCategory(long id) {
    categoryService.deleteCategory(id);
  }
}
