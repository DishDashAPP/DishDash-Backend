package com.dish_dash.product.domain.mapper;

import com.dishDash.common.dto.*;
import com.dish_dash.product.domain.model.Category;
import com.dish_dash.product.domain.model.Food;
import com.dish_dash.product.domain.model.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
  ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

  CategoryDto categoryToDto(Category category);

  FoodDto foodToDto(Food food);

  MenuDto menuToDto(Menu menu);

  Menu dtoToMenu(MenuDto menuDto);

  Food dtoToFood(FoodDto foodDto);

  Category dtoToCategory(CategoryDto categoryDto);
}
