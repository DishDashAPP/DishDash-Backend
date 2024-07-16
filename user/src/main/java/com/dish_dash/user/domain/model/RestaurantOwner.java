package com.dish_dash.user.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
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
    private String username;


    @ElementCollection
    @CollectionTable(name = "restaurant_owner_menu_ids", joinColumns = @JoinColumn(name = "restaurant_owner_id"))
    @Column(name = "menu_id")
    private List<String> menuIds;

    @ElementCollection
    @CollectionTable(name = "restaurant_owner_active_order_ids", joinColumns = @JoinColumn(name = "restaurant_owner_id"))
    @Column(name = "active_order_id")
    private List<String> activeOrderIds;

    @ElementCollection
    @CollectionTable(name = "restaurant_owner_order_history_ids", joinColumns = @JoinColumn(name = "restaurant_owner_id"))
    @Column(name = "order_history_id")
    private List<String> orderHistoryIds;

    public RestaurantOwner(String id, String name, String phoneNumber, String address, String username) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.username = username;
    }

    public boolean addActiveOrderID(String orderID) {
        return activeOrderIds.add(orderID);
    }
}
