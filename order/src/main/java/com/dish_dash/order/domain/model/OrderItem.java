package com.dish_dash.order.domain.model;

import lombok.Data;
import lombok.Getter;

@Data
public class OrderItem {
    private String orderItemID;
    private Food food;
    private Price price;
    private int quantity;
    private String orderID;

    public OrderItem(String orderItemID, Food food, Price price, int quantity, String orderID) {
        this.orderItemID = orderItemID;
        this.food = food;
        this.price = price;
        this.quantity = quantity;
        this.orderID = orderID;
    }
}
