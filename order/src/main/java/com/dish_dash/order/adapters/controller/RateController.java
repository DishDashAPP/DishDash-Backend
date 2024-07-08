package com.dish_dash.order.adapters.controller;

import com.dish_dash.order.application.service.RateService;
import com.dish_dash.order.domain.model.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rates")
public class RateController {

    @Autowired
    private RateService rateService;

    @PostMapping("/order")
    public boolean setOrderRate(@RequestParam String customerID, @RequestParam String orderID, @RequestParam int point) {
        return rateService.setOrderRate(customerID, orderID, point);
    }

    @PostMapping("/delivery")
    public boolean setDeliveryRate(@RequestParam String customerID, @RequestParam String orderID, @RequestParam int point) {
        return rateService.setDeliveryRate(customerID, orderID, point);
    }

    @GetMapping("/delivery/{deliveryPersonID}")
    public Rate getDeliveryRate(@PathVariable String deliveryPersonID) {
        return rateService.getDeliveryRate(deliveryPersonID);
    }
}
