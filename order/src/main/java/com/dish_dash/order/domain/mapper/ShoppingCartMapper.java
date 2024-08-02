package com.dish_dash.order.domain.mapper;

import com.dishDash.common.dto.*;
import com.dish_dash.order.domain.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShoppingCartMapper {
  ShoppingCartMapper INSTANCE = Mappers.getMapper(ShoppingCartMapper.class);

  ShoppingCartDto shoppingCartToDto(ShoppingCart shoppingCart);

  ShoppingCart shoppingCartDtoToShoppingCart(ShoppingCartDto shoppingCartDto);

  ShoppingCartItem shoppingCartItemCreationDtoToShoppingCartItem(ShoppingCartItemCreateDto dto);
}
