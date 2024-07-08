package com.dish_dash.user.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "restaurant_owners")
public class RestaurantOwner implements User {

    @Id
    private String id;
    private String name;
    private String phoneNumber;
    private String address;
    private List<String> menuIds;
    private List<String> activeOrderIds;
    private List<String> orderHistoryIds;

    public RestaurantOwner(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public boolean addActiveOrderID(String orderID) {
        return true;
    }
}
