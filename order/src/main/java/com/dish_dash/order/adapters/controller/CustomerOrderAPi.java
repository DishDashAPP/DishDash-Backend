package com.dish_dash.order.adapters.controller;

import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.model.OrderItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "customer-order-service")
public interface CustomerOrderAPi {
    @PostMapping("/createOrder")
    Order createOrder(
            @RequestParam Long customerId,
            @RequestParam Long restaurantOwnerId,
            @RequestBody List<OrderItem> orderItems);

    @PostMapping("/modifyOrder")
    Order modifyOrder(@RequestParam Long orderId, @RequestBody List<OrderItem> orderItems);

    @PostMapping("/setOrderRate")
    boolean setOrderRate(
            @RequestParam Long customerId, @RequestParam Long orderId, @RequestParam int point);

    @PostMapping("/setDeliveryRate")
    boolean setDeliveryRate(
            @RequestParam Long customerId, @RequestParam Long orderId, @RequestParam int point);

    @GetMapping("/getCustomerOrders")
    List<Order> getCustomerOrders(@RequestParam Long customerId);

    @GetMapping("/getCustomerCurrentOrder")
    Order getCustomerCurrentOrder(@RequestParam Long customerId);
}
