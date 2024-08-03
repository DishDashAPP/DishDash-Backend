package com.dishDash.common.dto;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

  @JsonProperty("id")
  private long id;

  @JsonProperty("comment")
  private String comment;

  @JsonProperty("customer_id")
  private long customerId;

  @JsonProperty("order_id")
  private long orderId;
}
