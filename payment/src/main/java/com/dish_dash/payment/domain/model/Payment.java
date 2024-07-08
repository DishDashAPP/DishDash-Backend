package com.dish_dash.payment.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Payment {
    private UUID traceID;
    private Date dateTime;
    private String transactionID;

    public Payment(String transactionID) {
        this.traceID = generateTraceID();
        this.dateTime = new Date();
        this.transactionID = transactionID;
    }

    private UUID generateTraceID() {
        return UUID.randomUUID();
    }
}
