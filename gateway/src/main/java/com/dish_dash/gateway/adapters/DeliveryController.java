package com.dish_dash.gateway.adapters;

import com.dishDash.common.dto.InvoiceDto;
import com.dishDash.common.feign.delivery.DeliveryApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/delivery")
@RequiredArgsConstructor
public class DeliveryController {
  private final DeliveryApi deliveryApi;

  @GetMapping("/getInvoice")
  public InvoiceDto getInvoice(@RequestParam Long orderId) {
    return deliveryApi.getInvoice(orderId);
  }
}
