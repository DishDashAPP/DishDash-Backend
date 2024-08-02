package com.dishDash.common.dto;

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
public class MenuDto {
  @JsonProperty("restaurant_id")
  private long restaurantId;

  private List<FoodDto> foods;
  private List<CategoryViewDto> categories;
  private long id;
}
