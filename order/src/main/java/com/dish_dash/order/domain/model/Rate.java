package com.dish_dash.order.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Rate {
    @Id
    private String rateID;
    private int point;
    private String customerID;
    private String orderID;

    public Rate(int point, String customerID, String orderID) {
        this.point = point;
        this.customerID = customerID;
        this.orderID = orderID;
    }
}
