package com.dish_dash.payment.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Payment {
    private String traceID;
    private Date dateTime;
    private String transactionID;

    public Payment(String transactionID) {
        this.traceID = generateTraceID();
        this.dateTime = new Date();
        this.transactionID = transactionID;
    }

    private String generateTraceID() {
        // Logic to generate a unique trace ID
    }
}
