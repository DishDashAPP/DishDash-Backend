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
public class OrderItemDto {
  @JsonProperty("order_id")
  private Long orderId;

  @JsonProperty("food_id")
  private Long foodId;

  private Price price;
  private Integer quantity;
}
