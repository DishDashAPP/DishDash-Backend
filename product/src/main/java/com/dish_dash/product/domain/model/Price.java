package com.dish_dash.product.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Embeddable
@NoArgsConstructor
public class Price {
    private double amount;
    private CurrencyUnit unit;

    public Price(double amount, CurrencyUnit unit) {
        this.amount = amount;
        this.unit = unit;
    }
}
