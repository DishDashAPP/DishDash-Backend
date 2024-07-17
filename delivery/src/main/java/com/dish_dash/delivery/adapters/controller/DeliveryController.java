package com.dish_dash.delivery.adapters.controller;

import com.dishDash.common.dto.InvoiceDto;
import com.dishDash.common.dto.LocationDto;
import com.dishDash.common.feign.delivery.DeliveryApi;
import com.dish_dash.delivery.application.service.DeliveryService;
import com.dish_dash.delivery.domain.model.Invoice;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController implements DeliveryApi {

  private final DeliveryService deliveryService;

  @Override
  public boolean assignOrder(@RequestParam Long orderId, @RequestParam Long deliveryPersonId) {
    return deliveryService.assignOrder(orderId, deliveryPersonId);
  }

  @Override
  public InvoiceDto getInvoice(@RequestParam Long orderId) {
    return deliveryService.getInvoice(orderId);
  }
}
