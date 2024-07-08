package com.dish_dash.user.domain.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class RestaurantOwner implements User {
    @Id
    private String id;
    private String name;
    private String phoneNumber;
    private String email;
    @OneToMany
    private List<String> menus;
    @OneToMany
    private List<String> orders;
    @OneToMany
    private List<String> activeOrders;

    // Constructors, Getters, Setters, and Implement User methods
}