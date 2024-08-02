package com.dishDash.common.dto;

import com.dishDash.common.Price;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDto {
  private long id;

  @JsonProperty("customer_id")
  private long customerId;

  @JsonProperty("restaurant_owner_id")
  private long restaurantOwnerId;

  @JsonProperty("order_items")
  private List<OrderItemDto> orderItems;

  @JsonProperty("total_price")
  private Price totalPrice;
}
