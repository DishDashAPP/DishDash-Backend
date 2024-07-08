package com.dish_dash.user.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantOwner implements User {
    private String id;
    private String name;
    private String phoneNumber;
    private String address;
    private List<String> menuIds;
    private List<String> activeOrderIds;
    private List<String> orderHistoryIds;

    public void addActiveOrder(String orderId) {
        this.activeOrderIds.add(orderId);
    }

    @Override
    public User modifyProfile(User user) {
        if (user instanceof RestaurantOwner) {
            RestaurantOwner owner = (RestaurantOwner) user;
            this.name = owner.getName();
            this.phoneNumber = owner.getPhoneNumber();
            this.address = owner.getAddress();
        }
        return this;
    }
}
