package com.dish_dash.payment.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Transaction {
    private String id;
    private TransactionStatus status;
    private Price price;
    private String orderID;

    public Transaction(String orderID, Price price) {
        this.id = generateTransactionID();
        this.status = TransactionStatus.NOT_PAID;
        this.price = price;
        this.orderID = orderID;
    }

    private String generateTransactionID() {
        // Logic to generate a unique transaction ID
    }
}

