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
public class OrderItemCreateDto {
  @JsonProperty("order_id")
  private long orderId;

  @JsonProperty("food_id")
  private long foodId;

  private Integer quantity;
}
