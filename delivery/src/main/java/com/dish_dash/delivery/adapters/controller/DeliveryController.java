package com.dish_dash.delivery.adapters.controller;

import com.dish_dash.delivery.application.service.DeliveryService;
import com.dish_dash.delivery.domain.model.Invoice;
import com.dish_dash.user.domain.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @PostMapping("/setLocation")
    public boolean setLocation(@RequestBody Location location, @RequestParam String deliveryPersonId) {
        return deliveryService.setLocation(location, deliveryPersonId);
    }

    @GetMapping("/getLocation")
    public Location getLocation(@RequestParam String deliveryPersonId) {
        return deliveryService.getLocation(deliveryPersonId);
    }

    @PostMapping("/assignOrder")
    public boolean assignOrder(@RequestParam String orderId, @RequestParam String deliveryPersonId) {
        return deliveryService.assignOrder(orderId, deliveryPersonId);
    }

    @GetMapping("/getInvoice")
    public Invoice getInvoice(@RequestParam String orderId) {
        return deliveryService.getInvoice(orderId);
    }
}
