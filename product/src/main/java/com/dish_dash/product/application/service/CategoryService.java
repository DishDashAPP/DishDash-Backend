package com.dish_dash.product.application.service;

import com.dishDash.common.dto.CategoryCreationDto;
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
  public List<CategoryCreationDto> getAllCategories() {
    return categoryRepository.findAll().stream()
        .map(ProductMapper.INSTANCE::categoryToDto)
        .collect(Collectors.toList());
  }

  public CategoryCreationDto getCategoryById(Long id) {
    return categoryRepository.findById(id).map(ProductMapper.INSTANCE::categoryToDto).orElse(null);
  }

  public CategoryCreationDto saveCategory(CategoryCreationDto categoryCreationDto) {
    return ProductMapper.INSTANCE.categoryToDto(
        categoryRepository.save(ProductMapper.INSTANCE.dtoToCategory(categoryCreationDto)));
  }

  public void deleteCategory(Long id) {
    categoryRepository.deleteById(id);
  }

  public Category getReferenceCategory(Long id) {
    return categoryRepository.getReferenceById(id);
  }
}
