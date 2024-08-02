package com.dish_dash.product;

import com.dishDash.common.dto.CategoryCreationDto;
import com.dishDash.common.dto.CategoryViewDto;
import com.dish_dash.product.application.service.CategoryService;
import com.dish_dash.product.domain.model.Category;
import com.dish_dash.product.infrastructure.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CategoryServiceIntegrationTest {

  @Autowired private CategoryRepository categoryRepository;

  @Autowired private CategoryService categoryService;

  private CategoryCreationDto categoryCreationDto;

  @BeforeEach
  void setUp() {
    categoryRepository.deleteAll();

    categoryRepository.flush();
    categoryCreationDto = CategoryCreationDto.builder().name("CATEGORY_NAME").build();
  }

  @Test
  void getAllCategories_ShouldReturnAllCategories() {
    Category category = categoryRepository.save(Category.builder().name("CATEGORY_NAME").build());

    List<CategoryViewDto> categories = categoryService.getAllCategories();

    assertNotNull(categories, "Categories should not be null");
    assertEquals(1, categories.size(), "There should be exactly one category");
    assertEquals(
        category.getName(), categories.get(0).getName(), "Category name should be CATEGORY_NAME");
  }

  @Test
  void getCategoryById_ShouldReturnCategoryViewDto_WhenCategoryExists() {
    Category category = categoryRepository.save(Category.builder().name("CATEGORY_NAME").build());

    CategoryViewDto categoryViewDto = categoryService.getCategoryById(category.getId());

    assertNotNull(categoryViewDto, "Category should not be null");
    assertEquals(category.getId(), categoryViewDto.getId(), "Category ID should be 1L");
    assertEquals(
        "CATEGORY_NAME", categoryViewDto.getName(), "Category name should be CATEGORY_NAME");
  }

  @Test
  void getCategoryById_ShouldReturnNull_WhenCategoryDoesNotExist() {
    CategoryViewDto categoryViewDto = categoryService.getCategoryById(2L);

    assertNull(categoryViewDto, "Category should be null for non-existing category");
  }

  @Test
  void saveCategory_ShouldSaveAndReturnCategoryCreationDto() {
    CategoryCreationDto savedCategoryDto = categoryService.saveCategory(categoryCreationDto, userId);

    assertNotNull(savedCategoryDto, "Saved category should not be null");
    assertEquals(
        "CATEGORY_NAME", savedCategoryDto.getName(), "Category name should be CATEGORY_NAME");

    Category savedCategory = categoryRepository.findById(1L).orElse(null);
    assertNotNull(savedCategory, "Category in repository should not be null");
    assertEquals("CATEGORY_NAME", savedCategory.getName(), "Category name should match");
  }

  @Test
  void deleteCategory_ShouldRemoveCategory() {
    Category category = categoryRepository.save(Category.builder().name("CATEGORY_NAME").build());

    categoryService.deleteCategory(category.getId());

    Category deletedCategory = categoryRepository.findById(category.getId()).orElse(null);

    assertNull(deletedCategory, "Category should be null after deletion");
  }

  @Test
  void getReferenceCategory_ShouldReturnCategoryReference_WhenCategoryExists() {
    Category category = categoryRepository.save(Category.builder().name("CATEGORY_NAME").build());

    Category referenceCategory = categoryService.getReferenceCategory(category.getId());

    assertNotNull(referenceCategory, "Reference category should not be null");
    assertEquals(category.getId(), referenceCategory.getId(), "Reference category ID should be 1L");
  }
}
