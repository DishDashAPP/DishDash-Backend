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
public class MenuDto {
  @JsonProperty("restaurant_id")
  private String restaurantId;

  private long id;
}
