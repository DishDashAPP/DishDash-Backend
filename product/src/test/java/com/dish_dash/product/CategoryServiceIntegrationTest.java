package com.dish_dash.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.dishDash.common.dto.CategoryCreationDto;
import com.dishDash.common.dto.CategoryViewDto;
import com.dishDash.common.enums.ErrorCode;
import com.dishDash.common.exception.CustomException;
import com.dish_dash.product.application.service.CategoryService;
import com.dish_dash.product.domain.model.Category;
import com.dish_dash.product.domain.model.Menu;
import com.dish_dash.product.infrastructure.repository.CategoryRepository;
import com.dish_dash.product.infrastructure.repository.MenuRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CategoryServiceIntegrationTest {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private MenuRepository menuRepository;

  private CategoryCreationDto categoryCreationDto;
  private Menu menu;

  @BeforeEach
  void setUp() {
    categoryRepository.deleteAll();
    menuRepository.deleteAll();

    categoryCreationDto = CategoryCreationDto.builder().name("CATEGORY_NAME").build();
    menu = menuRepository.save(Menu.builder().restaurantId(1).build());
  }

  @Test
  void getAllCategories_ShouldReturnAllCategories() {
    Category category = categoryRepository.save(
        Category.builder().name("CATEGORY_NAME").menu(menu).build());

    List<CategoryViewDto> categories = categoryService.getAllCategories(menu.getRestaurantId());

    assertNotNull(categories, "Categories should not be null");
    assertEquals(1, categories.size(), "There should be exactly one category");
    assertEquals(category.getName(), categories.get(0).getName(),
        "Category name should be CATEGORY_NAME");
  }

  @Test
  void getCategoryById_ShouldReturnCategoryViewDto_WhenCategoryExists() {
    Category category = categoryRepository.save(
        Category.builder().name("CATEGORY_NAME").menu(menu).build());

    CategoryViewDto categoryViewDto = categoryService.getCategoryById(category.getId());

    assertNotNull(categoryViewDto, "Category should not be null");
    assertEquals(category.getId(), categoryViewDto.getId(), "Category ID should match");
    assertEquals("CATEGORY_NAME", categoryViewDto.getName(),
        "Category name should be CATEGORY_NAME");
  }

  @Test
  void getCategoryById_ShouldThrowException_WhenCategoryDoesNotExist() {
    long nonExistingCategoryId = 2L;

    CustomException exception = assertThrows(CustomException.class, () -> {
      categoryService.getCategoryById(nonExistingCategoryId);
    });

    assertEquals(ErrorCode.NOT_FOUND, exception.getErrorCode(), "Error code should be NOT_FOUND");
    assertEquals("Category not found for ID: " + nonExistingCategoryId, exception.getMessage(),
        "Exception message should indicate the category was not found");
  }

  @Test
  void saveCategory_ShouldSaveAndReturnCategoryCreationDto() {
    CategoryCreationDto savedCategoryDto = categoryService.saveCategory(categoryCreationDto,
        menu.getRestaurantId());

    assertNotNull(savedCategoryDto, "Saved category should not be null");
    assertEquals("CATEGORY_NAME", savedCategoryDto.getName(),
        "Category name should be CATEGORY_NAME");
  }

  @Test
  void deleteCategory_ShouldRemoveCategory() {
    Category category = categoryRepository.save(
        Category.builder().name("CATEGORY_NAME").menu(menu).build());

    categoryService.deleteCategory(category.getId());

    Optional<Category> deletedCategory = categoryRepository.findById(category.getId());
    assertFalse(deletedCategory.isPresent(), "Category should be null after deletion");
  }

  @Test
  void getReferenceCategory_ShouldReturnCategoryReference_WhenCategoryExists() {
    Category category = categoryRepository.save(
        Category.builder().name("CATEGORY_NAME").menu(menu).build());

    Category referenceCategory = categoryService.getReferenceCategory(category.getId());

    assertNotNull(referenceCategory, "Reference category should not be null");
    assertEquals(category.getId(), referenceCategory.getId(), "Reference category ID should match");
  }
}
