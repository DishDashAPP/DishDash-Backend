package com.dishDash.common.feign.Product;

import com.dishDash.common.dto.CategoryCreationDto;
import com.dishDash.common.dto.CategoryViewDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-service", contextId = "category-product-service", path = "/category")
public interface CategoryApi {

  @GetMapping()
  List<CategoryViewDto> getAllCategories(@RequestParam long userId);

  @GetMapping("/{id}")
  CategoryViewDto getCategoryById(@PathVariable long id);

  @PostMapping()
  CategoryCreationDto createCategory(
      @RequestParam long userId, @RequestBody CategoryCreationDto categoryDto);

  @DeleteMapping("/{id}")
  void deleteCategory(@PathVariable long id);
}
