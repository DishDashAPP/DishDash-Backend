package com.dishDash.common.feign.delivery;

import com.dishDash.common.dto.InvoiceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "delivery-service", contextId = "delivery-service", path = "/delivery")
public interface DeliveryApi {
  @PostMapping("/assignOrder")
  boolean assignOrder(@RequestParam long orderId, @RequestParam long deliveryPersonId);

  @GetMapping("/getInvoice")
  InvoiceDto getInvoice(@RequestParam long orderId);
}
