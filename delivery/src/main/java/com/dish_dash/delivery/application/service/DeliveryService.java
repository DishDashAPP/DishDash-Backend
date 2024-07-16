package com.dish_dash.delivery.application.service;

import com.dishDash.common.dto.LocationDto;
import com.dish_dash.delivery.domain.model.Invoice;
import com.dish_dash.delivery.infrastructure.repository.InvoiceRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

  @Autowired private InvoiceRepository invoiceRepository;

  //  @Autowired private LocationRepository locationRepository;

  public boolean setLocation(LocationDto locationDto, String deliveryPersonId) {
    //    return locationRepository.modify(location);
    return true;
  }

  public LocationDto getLocation(String deliveryPersonId) {
    //    return locationRepository.findByID(deliveryPersonId);
    return LocationDto.builder().build();
  }

  public boolean assignOrder(Long orderId, Long deliveryPersonId) {
    Optional<Invoice> invoiceOptional = invoiceRepository.findById(orderId);
    if (invoiceOptional.isEmpty()) return false;
    invoiceOptional.get().setDeliveryPersonId(deliveryPersonId);
    invoiceRepository.save(invoiceOptional.get());
    return true;
  }

  public Invoice getInvoice(Long orderId) {
    return invoiceRepository.findById(orderId).orElse(null);
  }
}
