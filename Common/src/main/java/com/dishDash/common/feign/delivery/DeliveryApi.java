package com.dishDash.common.feign.delivery;

import com.dishDash.common.dto.InvoiceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "delivery-service")
public interface DeliveryApi {
  @PostMapping("/assignOrder")
  boolean assignOrder(@RequestParam Long orderId, @RequestParam Long deliveryPersonId);

  @GetMapping("/getInvoice")
  InvoiceDto getInvoice(@RequestParam Long orderId);
}
