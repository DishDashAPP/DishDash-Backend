package com.dish_dash.product.application.service;

import com.dishDash.common.dto.CategoryDto;
import com.dish_dash.product.domain.mapper.ProductMapper;
import com.dish_dash.product.infrastructure.repository.CategoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public List<CategoryDto> getAllCategories() {
    return categoryRepository.findAll().stream()
        .map(ProductMapper.INSTANCE::categoryToDto)
        .collect(Collectors.toList());
  }

  public CategoryDto getCategoryById(Long id) {
    return categoryRepository.findById(id).map(ProductMapper.INSTANCE::categoryToDto).orElse(null);
  }

  public CategoryDto saveCategory(CategoryDto categoryDto) {
    return ProductMapper.INSTANCE.categoryToDto(
        categoryRepository.save(ProductMapper.INSTANCE.dtoToCategory(categoryDto)));
  }

  public void deleteCategory(Long id) {
    categoryRepository.deleteById(id);
  }
}
