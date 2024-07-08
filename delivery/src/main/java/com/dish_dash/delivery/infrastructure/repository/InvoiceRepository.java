package com.dish_dash.delivery.infrastructure.repository;

import com.dish_dash.delivery.domain.model.Invoice;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository {
    Invoice findByID(String orderID);
    Invoice create(Customer customer, DeliveryPerson deliveryPerson, Order order);
    boolean modify(Invoice invoice);
    Invoice getInvoice(String orderID);
}
