package com.dishDash.common.feign.Product;

import com.dishDash.common.dto.CategoryDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-service", contextId = "category-product-service")
public interface CategoryApi {

  @GetMapping()
  List<CategoryDto> getAllCategories();

  @GetMapping("/{id}")
  CategoryDto getCategoryById(@PathVariable Long id);

  @PostMapping()
  CategoryDto createCategory(@RequestBody CategoryDto categoryDto);

  @DeleteMapping("/{id}")
  void deleteCategory(@PathVariable Long id);
}
