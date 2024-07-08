package com.dish_dash.order.adapters.controller;

import com.dish_dash.order.application.service.OrderService;
import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.model.OrderStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderID}")
    public Order viewOrder(@PathVariable String orderID) {
        return orderService.viewOrder(orderID);
    }

    @GetMapping("/{orderID}/status")
    public OrderStatus getOrderStatus(@PathVariable String orderID) {
        return orderService.getOrderStatus(orderID);
    }

    @PostMapping("/prepare")
    public boolean prepareOrder(@RequestBody Order order) {
        return orderService.prepareOrder(order);
    }
}
