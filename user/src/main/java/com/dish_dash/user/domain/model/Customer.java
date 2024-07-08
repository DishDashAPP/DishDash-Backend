package com.dish_dash.user.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements User {
    private String id;
    private String name;
    private String phoneNumber;
    private String address;
    private List<String> orderIds;

    public Order getCurrentOrder() {
        // Logic to get the current order
        return null;
    }

    public List<Order> getOrders() {
        // Logic to get all orders
        return null;
    }

    @Override
    public User modifyProfile(User user) {
        if (user instanceof Customer) {
            Customer customer = (Customer) user;
            this.name = customer.getName();
            this.phoneNumber = customer.getPhoneNumber();
            this.address = customer.getAddress();
        }
        return this;
    }
}
