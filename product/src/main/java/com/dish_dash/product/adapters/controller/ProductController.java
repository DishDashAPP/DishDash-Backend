package com.dish_dash.product.adapters.controller;

import com.dish_dash.product.application.service.MenuService;
import com.dish_dash.product.application.service.FoodService;
import com.dish_dash.product.application.service.CategoryService;
import com.dish_dash.product.domain.model.Menu;
import com.dish_dash.product.domain.model.Food;
import com.dish_dash.product.domain.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private FoodService foodService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/addFoodItem")
    public boolean addFoodItem(@RequestParam String name, @RequestParam String description, @RequestParam double price,
                               @RequestParam int stock, @RequestParam String menuID) {
        return foodService.addFoodItem(name, description, price, stock, menuID);
    }

    @GetMapping("/getMenu")
    public Menu getMenu(@RequestParam String restaurantOwnerID) {
        return menuService.getMenu(restaurantOwnerID);
    }

    @DeleteMapping("/deleteFood")
    public boolean deleteFood(@RequestParam String foodID) {
        return foodService.deleteFood(foodID);
    }

    @PutMapping("/modifyFood")
    public boolean modifyFood(@RequestBody Food food) {
        return foodService.modifyFood(food);
    }

    @PostMapping("/addCategory")
    public boolean addCategory(@RequestParam String name) {
        return categoryService.addCategory(name);
    }

    @PostMapping("/createMenu")
    public Menu createMenu(@RequestParam String restaurantID) {
        return menuService.createMenu(restaurantID);
    }
}
