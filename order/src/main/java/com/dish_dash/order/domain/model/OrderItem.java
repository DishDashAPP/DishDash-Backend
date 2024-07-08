package com.dish_dash.order.domain.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderItem {
    @Id
    private String orderItemID;
    private String orderID;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Food food;

    @Embedded
    private Price price;

    private int quantity;

    public OrderItem(Food food, Price price, int quantity) {
        this.food = food;
        this.price = price;
        this.quantity = quantity;
        this.orderItemID = generateOrderItemID(); // Implement a method to generate a unique ID
    }

    private String generateOrderItemID() {
        // Implement a unique ID generation logic
        return UUID.randomUUID().toString();
    }
}
