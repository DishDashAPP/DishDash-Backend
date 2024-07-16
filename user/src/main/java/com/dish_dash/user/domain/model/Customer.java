package com.dish_dash.user.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer implements User {

    @Id
    private String id;
    private String name;
    private String phoneNumber;
    private String address;
    private String username;

    @ElementCollection
    @CollectionTable(name = "customer_orders", joinColumns = @JoinColumn(name = "customer_id"))
    @Column(name = "order_id")
    private List<String> orderIds = new ArrayList<>();

    public Customer(String id, String name, String phoneNumber, String address, String username) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.username = username;
    }
}
