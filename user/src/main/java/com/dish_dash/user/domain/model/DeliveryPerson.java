package com.dish_dash.user.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPerson implements User {
    private String id;
    private String name;
    private String phoneNumber;
    private Location location;
    private Order currentOrder;
    private DeliveryPersonStatus status;

    public boolean setLocation(Location location) {
        this.location = location;
        return true;
    }

    public boolean assignOrder(String orderId) {
        // Logic to assign order to the delivery person
        return true;
    }

    public Order getAssignedOrder(String orderId) {
        // Logic to get the assigned order
        return null;
    }

    @Override
    public User modifyProfile(User user) {
        if (user instanceof DeliveryPerson) {
            DeliveryPerson deliveryPerson = (DeliveryPerson) user;
            this.name = deliveryPerson.getName();
            this.phoneNumber = deliveryPerson.getPhoneNumber();
            this.location = deliveryPerson.getLocation();
            this.status = deliveryPerson.getStatus();
        }
        return this;
    }

    // Additional methods
    public Location getLocation() {
        return location;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }


