package com.dishDash.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartItemCreateDto {
  @JsonProperty("shopping_cart_id")
  private long shoppingCartId;

  @JsonProperty("food_id")
  private long foodId;

  private Integer quantity;
}
