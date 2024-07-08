package com.dish_dash.payment.domain.model;

import lombok.Data;

@Data
public class Price {
    private double amount;
    private CurrencyUnit unit;

    public Price(double amount, CurrencyUnit unit) {
        this.amount = amount;
        this.unit = unit;
    }
}
