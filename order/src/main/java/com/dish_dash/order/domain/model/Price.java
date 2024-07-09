package com.dish_dash.order.domain.model;

import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class Price {
    private double amount;
    private CurrencyUnit unit;

    public Price(double amount, CurrencyUnit unit) {
        this.amount = amount;
        this.unit = unit;
    }

    public double getAmount() {
        return amount;
    }

    public CurrencyUnit getUnit() {
        return unit;
    }
}
