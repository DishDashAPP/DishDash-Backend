package com.dishDash.common.dto;

import com.dishDash.common.Price;
import com.dishDash.common.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
  private long id;
  private long customerId;
  private long restaurantOwnerId;
  private List<OrderItemDto> orderItems;
  private OrderStatus status;
  private Price totalPrice;
  private Timestamp createTime;
  private DeliveryPersonDto deliveryPersonDto;
  private CustomerDto customerDto;
}
