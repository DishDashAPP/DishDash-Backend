package com.dish_dash.order.adapters.controller;

import com.dish_dash.order.application.service.DeliveryPersonOrderService;
import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.model.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deliveryPersonOrder")
public class DeliveryPersonOrderController {

    @Autowired
    private DeliveryPersonOrderService deliveryPersonOrderService;

    @PostMapping("/updateOrderStatus")
    public boolean updateOrderStatusByDeliveryPerson(@RequestParam String orderID, @RequestParam OrderStatus status) {
        return deliveryPersonOrderService.updateOrderStatusByDeliveryPerson(orderID, status);
    }

    @GetMapping("/getCurrentOrder")
    public Order getDeliveryPersonCurrentOrder(@RequestParam String deliveryPersonID) {
        return deliveryPersonOrderService.getDeliveryPersonCurrentOrder(deliveryPersonID);
    }
}

