package com.dishDash.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantOwnerDto {
  private String name;
  private String phoneNumber;
  private String address;
  private String username;
}
