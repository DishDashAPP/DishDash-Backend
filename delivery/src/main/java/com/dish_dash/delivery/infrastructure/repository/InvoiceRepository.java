package com.dish_dash.delivery.infrastructure.repository;

import com.dish_dash.delivery.domain.model.Invoice;
import com.dish_dash.order.domain.model.Order;
import com.dish_dash.user.domain.model.Customer;
import com.dish_dash.user.domain.model.DeliveryPerson;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository {
    Invoice findByID(String orderID);
    Invoice create(Customer customer, DeliveryPerson deliveryPerson, Order order);
    boolean modify(Invoice invoice);
    Invoice getInvoice(String orderID);
}
