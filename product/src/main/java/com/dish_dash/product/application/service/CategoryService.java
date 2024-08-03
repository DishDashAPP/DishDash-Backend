package com.dish_dash.product.application.service;

import com.dishDash.common.dto.CategoryCreationDto;
import com.dishDash.common.dto.CategoryViewDto;
import com.dishDash.common.enums.ErrorCode;
import com.dishDash.common.exception.CustomException;
import com.dish_dash.product.domain.mapper.ProductMapper;
import com.dish_dash.product.domain.model.Category;
import com.dish_dash.product.domain.model.Menu;
import com.dish_dash.product.infrastructure.repository.CategoryRepository;
import com.dish_dash.product.infrastructure.repository.MenuRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final MenuRepository menuRepository;

  public List<CategoryViewDto> getAllCategories(long userId) {
    log.info("Retrieving all categories for restaurant ID: {}", userId);
    return categoryRepository.findAllByMenu_RestaurantId(userId).stream()
        .map(ProductMapper.INSTANCE::categoryToViewDto).toList();
  }

  public CategoryViewDto getCategoryById(long id) {
    log.info("Retrieving category by ID: {}", id);
    return categoryRepository.findById(id).map(ProductMapper.INSTANCE::categoryToViewDto)
        .orElseThrow(() -> {
          log.error("Category with ID: {} not found", id);
          return new CustomException(ErrorCode.NOT_FOUND, "Category not found for ID: " + id);
        });
  }

  public CategoryCreationDto saveCategory(CategoryCreationDto categoryCreationDto, long userId) {
    log.info("Saving category for restaurant ID: {}", userId);

    Assert.notNull(categoryCreationDto, "Category creation DTO must not be null");
    Assert.hasText(categoryCreationDto.getName(), "Category name must not be empty");

    Menu menu = menuRepository.findByRestaurantId(userId).orElseGet(() -> createNewMenu(userId));

    Category category = ProductMapper.INSTANCE.dtoToCategory(categoryCreationDto);
    category.setMenu(menu);
    categoryRepository.save(category);

    log.info("Category saved with ID: {}", category.getId());
    return ProductMapper.INSTANCE.categoryToDto(category);
  }

  public void deleteCategory(long id) {
    log.info("Deleting category with ID: {}", id);

    if (!categoryRepository.existsById(id)) {
      log.error("Attempt to delete non-existing category with ID: {}", id);
      throw new CustomException(ErrorCode.NOT_FOUND, "Category not found for ID: " + id);
    }

    categoryRepository.deleteById(id);
    log.info("Category with ID: {} deleted successfully", id);
  }

  public Category getReferenceCategory(long id) {
    log.info("Getting reference to category by ID: {}", id);

    return categoryRepository.findById(id).orElseThrow(() -> {
      log.error("Reference category ID: {} not found", id);
      return new CustomException(ErrorCode.NOT_FOUND, "Category not found for ID: " + id);
    });
  }

  private Menu createNewMenu(long userId) {
    log.warn("No existing menu found for user ID: {}. Creating new menu.", userId);
    Menu newMenu = new Menu();
    newMenu.setRestaurantId(userId);
    menuRepository.save(newMenu);
    log.info("New menu created with ID: {}", newMenu.getId());
    return newMenu;
  }
}
