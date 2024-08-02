package com.dishDash.common.dto;

import com.dishDash.common.Price;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartItemDto {
  @JsonProperty("shopping_cart_id")
  private long shoppingCartId;

  @JsonProperty("food_id")
  private long foodId;

  private String name;
  private Price price;
  private Integer quantity;
}
