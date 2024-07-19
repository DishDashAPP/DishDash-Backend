package com.dishDash.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Time;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {
  private String id;

  @JsonProperty("customer_id")
  private Long customerId;

  @JsonProperty("order_id")
  private Long orderId;

  @JsonProperty("delivery_person_id")
  private Long deliveryPersonId;

  @JsonProperty("create_time")
  private Time creationTime;
}
