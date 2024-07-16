package com.dish_dash.delivery.adapters.controller;

import com.dishDash.common.dto.LocationDto;
import com.dish_dash.delivery.application.service.DeliveryService;
import com.dish_dash.delivery.domain.model.Invoice;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {

  private final DeliveryService deliveryService;

  @PostMapping("/setLocation")
  public boolean setLocation(
      @RequestBody LocationDto locationDto, @RequestParam String deliveryPersonId) {
    return deliveryService.setLocation(locationDto, deliveryPersonId);
  }

  @GetMapping("/getLocation")
  public LocationDto getLocation(@RequestParam String deliveryPersonId) {
    return deliveryService.getLocation(deliveryPersonId);
  }

  @PostMapping("/assignOrder")
  public boolean assignOrder(@RequestParam Long orderId, @RequestParam Long deliveryPersonId) {
    return deliveryService.assignOrder(orderId, deliveryPersonId);
  }

  @GetMapping("/getInvoice")
  public Invoice getInvoice(@RequestParam Long orderId) {
    return deliveryService.getInvoice(orderId);
  }
}
