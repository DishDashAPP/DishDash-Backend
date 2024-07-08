package com.dish_dash.order.domain.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Order {
    @Id
    private String orderID;
    private Date orderDate;
    private OrderStatus status;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    private String restaurantOwnerID;
    private String customerID;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Review review;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Rate restaurantRate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Rate deliveryPersonRate;

    @Embedded
    private Price totalPrice;

    public Order(String customerID, String restaurantOwnerID, List<OrderItem> orderItems) {
        this.customerID = customerID;
        this.restaurantOwnerID = restaurantOwnerID;
        this.orderItems = orderItems;
        this.orderDate = new Date();
        this.status = OrderStatus.PREPARING;
        calculateTotalPrice();
    }

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }

    public void calculateTotalPrice() {
        double total = orderItems.stream().mapToDouble(item -> item.getPrice().getAmount()).sum();
        this.totalPrice = new Price(total, CurrencyUnit.TOMAN);
    }

    public boolean setReview(Review review) {
        if (this.review == null) {
            this.review = review;
            return true;
        }
        return false;
    }

    public boolean setRate(Rate rate) {
        if (rate != null) {
            if (rate.getCustomerID().equals(this.customerID) && rate.getOrderID().equals(this.orderID)) {
                this.restaurantRate = rate;
                return true;
            }
        }
        return false;
    }
}
