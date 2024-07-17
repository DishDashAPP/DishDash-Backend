package com.dishDash.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {
    private String invoiceID;
    private Long customerId;
    private Long orderId;
    private Long deliveryPersonId;
    private Time creationTime;
}
