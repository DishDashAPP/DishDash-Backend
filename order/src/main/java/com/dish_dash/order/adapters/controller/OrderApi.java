package com.dish_dash.order.adapters.controller;

import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.model.OrderStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "order-service")
public interface OrderApi {
    @GetMapping("/viewOrder")
    Order viewOrder(@RequestParam Long orderID);

    @GetMapping("/getOrderStatus")
    OrderStatus getOrderStatus(@RequestParam Long orderID);

    @PostMapping("/prepareOrder")
    boolean prepareOrder(@RequestParam Order order);
}
