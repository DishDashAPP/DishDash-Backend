package com.dishDash.common.feign.Product;

import com.dishDash.common.dto.CategoryCreationDto;

import java.util.List;

import com.dishDash.common.dto.CategoryViewDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-service", contextId = "category-product-service", path = "/category")
public interface CategoryApi {

  @GetMapping()
  List<CategoryViewDto> getAllCategories();

  @GetMapping("/{id}")
  CategoryViewDto getCategoryById(@PathVariable Long id);

  @PostMapping()
  CategoryCreationDto createCategory(@RequestBody CategoryCreationDto categoryDto);

  @DeleteMapping("/{id}")
  void deleteCategory(@PathVariable Long id);
}
