package com.dish_dash.payment.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Transaction {
    private UUID id;
    private TransactionStatus status;
    private Price price;
    private String orderID;

    public Transaction(String orderID, Price price) {
        this.id = generateTransactionID();
        this.status = TransactionStatus.NOT_PAID;
        this.price = price;
        this.orderID = orderID;
    }

    private UUID generateTransactionID() {
        return UUID.randomUUID();
    }
}

