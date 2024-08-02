package com.dish_dash.product.application.service;

import com.dishDash.common.dto.CategoryCreationDto;
import com.dishDash.common.dto.CategoryViewDto;
import com.dishDash.common.enums.ErrorCode;
import com.dishDash.common.exception.CustomException;
import com.dish_dash.product.domain.mapper.ProductMapper;
import com.dish_dash.product.domain.model.Category;
import com.dish_dash.product.infrastructure.repository.CategoryRepository;
import com.dish_dash.product.infrastructure.repository.MenuRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final MenuRepository menuRepository;

  public List<CategoryViewDto> getAllCategories(Long userId) {
    return categoryRepository.findAllByMenu_RestaurantId(userId).stream()
        .map(ProductMapper.INSTANCE::categoryToViewDto)
        .collect(Collectors.toList());
  }

  public CategoryViewDto getCategoryById(long id) {
    return categoryRepository
        .findById(id)
        .map(ProductMapper.INSTANCE::categoryToViewDto)
        .orElse(null);
  }

  public CategoryCreationDto saveCategory(CategoryCreationDto categoryCreationDto, long userId) {
    var menu = menuRepository.findByRestaurantId(userId);
    if (menu.isEmpty()) {
      throw new CustomException(ErrorCode.UNPROCESSABLE_ENTITY, "Restaturant Menu not found");
    }
    var category = ProductMapper.INSTANCE.dtoToCategory(categoryCreationDto);
    category.setMenu(menu.get());

    categoryRepository.save(category);
    return ProductMapper.INSTANCE.categoryToDto(category);
  }

  public void deleteCategory(long id) {
    categoryRepository.deleteById(id);
  }

  public Category getReferenceCategory(long id) {
    return categoryRepository.getReferenceById(id);
  }
}
