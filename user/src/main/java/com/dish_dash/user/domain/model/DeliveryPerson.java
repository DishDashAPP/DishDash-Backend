package com.dish_dash.user.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.stream.Location;

@Data
@NoArgsConstructor
@Entity
@Table(name = "delivery_persons")
public class DeliveryPerson implements User {

    @Id
    private String id;
    private String name;
    private String phoneNumber;
    private Location location;
    private String currentOrderId;
    private DeliveryPersonStatus status;

    public DeliveryPerson(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean setLocation(Location location) {
        this.location = location;
        return true;
    }

    public boolean assignOrder(String orderID) {
        return true;
    }

    public Order getAssignedOrder(String orderID) {
        return currentOrder;
    }

    public boolean setStatus(DeliveryPersonStatus status) {
        this.status = status;
        return true;
    }
}
