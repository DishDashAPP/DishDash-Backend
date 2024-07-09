package com.dish_dash.delivery.application.service;

import com.dish_dash.delivery.domain.model.Invoice;
import com.dish_dash.user.domain.model.DeliveryPerson;
import com.dish_dash.user.domain.model.Location;
import com.dish_dash.delivery.infrastructure.repository.InvoiceRepository;
import com.dish_dash.user.adapters.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private LocationRepository locationRepository;

    public boolean setLocation(Location location, String deliveryPersonId) {
        return locationRepository.modify(location);
    }

    public Location getLocation(String deliveryPersonId) {
        return locationRepository.findByID(deliveryPersonId);
    }

    public boolean assignOrder(String orderId, String deliveryPersonId) {
        Invoice invoice = invoiceRepository.getInvoice(orderId);
        invoice.setDeliveryPerson(new DeliveryPerson());
        return invoiceRepository.modify(invoice);
    }

    public Invoice getInvoice(String orderId) {
        return invoiceRepository.getInvoice(orderId);
    }
}
