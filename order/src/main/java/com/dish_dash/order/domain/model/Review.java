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
public class Review {
    @Id
    private String reviewID;
    private String comment;
    private String customerID;
    private String orderID;

    public Review(String comment, String customerID, String orderID) {
        this.comment = comment;
        this.customerID = customerID;
        this.orderID = orderID;
    }
}
