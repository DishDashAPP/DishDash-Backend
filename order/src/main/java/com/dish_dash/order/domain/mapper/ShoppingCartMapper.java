package com.dish_dash.order.domain.mapper;

import com.dishDash.common.dto.*;
import com.dish_dash.order.domain.model.*;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShoppingCartMapper {
  ShoppingCartMapper INSTANCE = Mappers.getMapper(ShoppingCartMapper.class);

  ShoppingCartDto shoppingCartToDto(ShoppingCart order);

  ShoppingCart shoppingCartDtoToShoppingCart(ShoppingCartDto orderDto);

  default double calculateTotalPrice(List<ShoppingCartItem> shoppingCartItems) {
    return shoppingCartItems.stream()
            .mapToDouble(shoppingCartItem -> shoppingCartItem.getPrice().getAmount())
            .sum();
  }

  ShoppingCartItem shoppingCartItemCreationDtoToShoppingCartItem(ShoppingCartItemCreateDto dto);
}
