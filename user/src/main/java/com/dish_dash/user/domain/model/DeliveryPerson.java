package com.dish_dash.user.domain.model;

import com.dish_dash.order.domain.model.Order;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    private Order currentOrder;
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
        // logic to assign order
        return true;
    }

    public Order getAssignedOrder(String orderID) {
        // logic to get assigned order
        return currentOrder;
    }

    public boolean setStatus(DeliveryPersonStatus status) {
        this.status = status;
        return true;
    }
}
