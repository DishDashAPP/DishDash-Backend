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
public class FoodDto {
  private String name;
  private String description;
  private Integer stock;
  private Long price;

  @JsonProperty("category_id")
  private Long CategoryId;
}