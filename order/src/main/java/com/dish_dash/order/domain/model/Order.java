package com.dish_dash.order.domain.model;

import lombok.Data;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Data
public class Order {
    private String orderID;
    private Date orderDate;
    private OrderStatus status;
    private List<OrderItem> orderItems;
    private String restaurantOwnerID;
    private String customerID;
    private Review review;
    private Rate restaurantRate;
    private Rate deliveryPersonRate;
    private Price totalPrice;

    public Order(String orderID, Date orderDate, OrderStatus status, List<OrderItem> orderItems, String restaurantOwnerID, String customerID) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.status = status;
        this.orderItems = orderItems;
        this.restaurantOwnerID = restaurantOwnerID;
        this.customerID = customerID;
        this.totalPrice = calculateTotalPrice();
    }

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }

    public Price calculateTotalPrice() {
        return orderItems.stream().map(OrderItem::getPrice).reduce(new Price(0, CurrencyUnit.TOMAN), Price::add);
    }
}
