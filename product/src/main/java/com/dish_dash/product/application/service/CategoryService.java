package com.dish_dash.product.application.service;

import com.dishDash.common.dto.CategoryCreationDto;
import com.dishDash.common.dto.CategoryViewDto;
import com.dish_dash.product.domain.mapper.ProductMapper;
import com.dish_dash.product.domain.model.Category;
import com.dish_dash.product.infrastructure.repository.CategoryRepository;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;
  public List<CategoryViewDto> getAllCategories() {
    return categoryRepository.findAll().stream()
        .map(ProductMapper.INSTANCE::categoryToViewDto)
        .collect(Collectors.toList());
  }

  public CategoryViewDto getCategoryById(long id) {
    return categoryRepository.findById(id).map(ProductMapper.INSTANCE::categoryToViewDto).orElse(null);
  }

  public CategoryCreationDto saveCategory(CategoryCreationDto categoryCreationDto) {
    return ProductMapper.INSTANCE.categoryToDto(
        categoryRepository.save(ProductMapper.INSTANCE.dtoToCategory(categoryCreationDto)));
  }

  public void deleteCategory(long id) {
    categoryRepository.deleteById(id);
  }

  public Category getReferenceCategory(long id) {
    return categoryRepository.getReferenceById(id);
  }
}
