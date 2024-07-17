package com.dishDash.common.feign.delivery;

import com.dishDash.common.dto.InvoiceDto;
import com.dishDash.common.dto.LocationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "delivery-service")
public interface DeliveryApi {
    @PostMapping("/assignOrder")
    public boolean assignOrder(@RequestParam Long orderId, @RequestParam Long deliveryPersonId);

    @GetMapping("/getInvoice")
    public InvoiceDto getInvoice(@RequestParam Long orderId);
}
