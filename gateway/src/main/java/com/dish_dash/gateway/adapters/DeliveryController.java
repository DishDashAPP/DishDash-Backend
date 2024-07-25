package com.dish_dash.gateway.adapters;

import com.dishDash.common.dto.InvoiceDto;
import com.dishDash.common.feign.delivery.DeliveryApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/delivery")
@RequiredArgsConstructor
public class DeliveryController {
  private final DeliveryApi deleteApi;

  @PostMapping("/assignOrder")
  boolean assignOrder(@RequestParam long orderId, @RequestParam long deliveryPersonId) {
    return deleteApi.assignOrder(orderId, deliveryPersonId);
  }

  @GetMapping("/getInvoice")
  InvoiceDto getInvoice(@RequestParam long orderId) {
    return deleteApi.getInvoice(orderId);
  }
}
