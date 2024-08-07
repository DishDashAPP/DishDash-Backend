package com.dishDash.common.dto;

import com.dishDash.common.Price;
import com.dishDash.common.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
  private long id;

  @JsonProperty("customer_id")
  private long customerId;

  @JsonProperty("restaurant_owner_id")
  private long restaurantOwnerId;

  @JsonProperty("restaurant_owner")
  private RestaurantOwnerDto restaurantOwnerDto;

  @JsonProperty("order_items")
  private List<OrderItemDto> orderItems;

  @JsonProperty("status")
  private OrderStatus status;

  @JsonProperty("create_price")
  private Price totalPrice;

  @JsonProperty("create_time")
  private Timestamp createTime;

  @JsonProperty("delivery_person")
  private DeliveryPersonDto deliveryPersonDto;

  @JsonProperty("customer_dto")
  private CustomerDto customerDto;
}
