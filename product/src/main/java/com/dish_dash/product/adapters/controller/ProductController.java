package com.dish_dash.product.adapters.controller;

import com.dish_dash.product.application.service.CategoryService;
import com.dish_dash.product.application.service.FoodService;
import com.dish_dash.product.application.service.MenuService;
import com.dish_dash.product.domain.model.Category;
import com.dish_dash.product.domain.model.Food;
import com.dish_dash.product.domain.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FoodService foodService;

    @Autowired
    private MenuService menuService;

    @PostMapping("/menus/{menuId}/foods")
    public Food addFoodToMenu(@PathVariable Long menuId, @RequestBody Food food) {
        return menuService.addFoodToMenu(menuId, food);
    }

    // Category endpoints
    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/categories/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping("/categories")
    public Category createCategory(@RequestBody Category category) {
        return categoryService.saveCategory(category);
    }

    @DeleteMapping("/categories/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

    // Food endpoints
    @GetMapping("/foods")
    public List<Food> getAllFoods() {
        return foodService.getAllFoods();
    }

    @GetMapping("/foods/{id}")
    public Food getFoodById(@PathVariable Long id) {
        return foodService.getFoodById(id);
    }

    @PostMapping("/foods")
    public Food createFood(@RequestBody Food food) {
        return foodService.saveFood(food);
    }

    @DeleteMapping("/foods/{id}")
    public void deleteFood(@PathVariable Long id) {
        foodService.deleteFood(id);
    }

    // Menu endpoints
    @GetMapping("/menus")
    public List<Menu> getAllMenus() {
        return menuService.getAllMenus();
    }

    @GetMapping("/menus/{id}")
    public Menu getMenuById(@PathVariable Long id) {
        return menuService.getMenuById(id);
    }

    @PostMapping("/menus")
    public Menu createMenu(@RequestBody Menu menu) {
        return menuService.saveMenu(menu);
    }

    @DeleteMapping("/menus/{id}")
    public void deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
    }
}
