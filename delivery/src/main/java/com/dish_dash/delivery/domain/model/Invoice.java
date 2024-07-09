package com.dish_dash.delivery.domain.model;

import com.dish_dash.order.domain.model.Order;
import com.dish_dash.user.domain.model.Customer;
import com.dish_dash.user.domain.model.DeliveryPerson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Invoice {
    @Id
    private String invoiceID;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Customer customer;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Order order;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private DeliveryPerson deliveryPerson;
    private Date dateTime;

    public Invoice(Customer customer, DeliveryPerson deliveryPerson, Order order) {
        this.customer = customer;
        this.deliveryPerson = deliveryPerson;
        this.order = order;
        this.dateTime = new Date();
        this.invoiceID = generateInvoiceID();
    }

    private String generateInvoiceID() {
        return "INV-" + System.currentTimeMillis();
    }
}
