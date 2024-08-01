package com.dishDash.common.dto;

import com.dishDash.common.Price;
import com.dishDash.common.enums.OrderStatus;
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
    private long customerId;
    private long restaurantOwnerId;
    private List<OrderItemDto> orderItems;
    private Price totalPrice;
}
