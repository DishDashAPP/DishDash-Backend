package com.dish_dash.order.adapters.controller;

import com.dish_dash.order.application.service.OrderService;
import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.model.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/viewOrder")
    public Order viewOrder(@RequestParam String orderID) {
        return orderService.viewOrder(orderID);
    }

    @GetMapping("/getOrderStatus")
    public OrderStatus getOrderStatus(@RequestParam String orderID) {
        return orderService.getOrderStatus(orderID);
    }

    @PostMapping("/prepareOrder")
    public boolean prepareOrder(@RequestParam Order order) {
        return orderService.prepareOrder(order);
    }
}

