package com.dish_dash.delivery.domain.model;

import com.dish_dash.user.domain.model.Customer;
import com.dish_dash.user.domain.model.DeliveryPerson;

import java.util.Date;

public class Invoice {
    private String invoiceID;
    private Customer customer;
    private Order order;
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
        // Logic to generate invoice ID
        return "INV-" + System.currentTimeMillis();
    }

    public Customer getCustomer() {
        return customer;
    }

    public Order getOrder() {
        return order;
    }

    public DeliveryPerson getDeliveryPerson() {
        return deliveryPerson;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public String getInvoiceID() {
        return invoiceID;
    }
}
