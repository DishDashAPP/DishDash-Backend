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
public class RestaurantCommentsDto {
  private float avg;

  @JsonProperty("number_of_rate")
  private int numberOfRate;

  @JsonProperty("number_of_review")
  private int numberOfReview;
}
