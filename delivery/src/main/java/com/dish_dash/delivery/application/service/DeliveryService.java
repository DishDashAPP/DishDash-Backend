package com.dish_dash.delivery.application.service;

import com.dishDash.common.dto.InvoiceDto;
import com.dishDash.common.dto.LocationDto;
import com.dish_dash.delivery.domain.mapper.DeliveryMapper;
import com.dish_dash.delivery.domain.model.Invoice;
import com.dish_dash.delivery.infrastructure.repository.InvoiceRepository;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final InvoiceRepository invoiceRepository;

    public boolean assignOrder(long orderId, long deliveryPersonId) {
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(orderId);
        if (invoiceOptional.isEmpty()) return false;
        invoiceOptional.get().setDeliveryPersonId(deliveryPersonId);
        invoiceRepository.save(invoiceOptional.get());
        return true;
    }

    public InvoiceDto getInvoice(long orderId) {
        return invoiceRepository.findById(orderId).map(DeliveryMapper.INSTANCE::invoiceToDto).orElse(null);
    }
}
