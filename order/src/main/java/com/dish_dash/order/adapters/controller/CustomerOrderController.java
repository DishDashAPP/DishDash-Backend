package com.dish_dash.order.adapters.controller;

import com.dish_dash.order.application.service.CustomerOrderService;
import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customerOrder")
public class CustomerOrderController {

    @Autowired
    private CustomerOrderService customerOrderService;

    @PostMapping("/createOrder")
    public Transaction createOrder(@RequestParam String customerID, @RequestParam String restaurantOwnerID, @RequestBody List<OrderItem> orderItems) {
        return customerOrderService.createOrder(customerID, restaurantOwnerID, orderItems);
    }

    @PostMapping("/modifyOrder")
    public Order modifyOrder(@RequestParam String orderID, @RequestBody List<OrderItem> orderItems) {
        return customerOrderService.modifyOrder(orderID, orderItems);
    }

    @PostMapping("/setOrderRate")
    public boolean setOrderRate(@RequestParam String customerID, @RequestParam String orderID, @RequestParam int point) {
        return customerOrderService.setOrderRate(customerID, orderID, point);
    }

    @PostMapping("/setDeliveryRate")
    public boolean setDeliveryRate(@RequestParam String customerID, @RequestParam String orderID, @RequestParam int point) {
        return customerOrderService.setDeliveryRate(customerID, orderID, point);
    }

    @GetMapping("/getCustomerOrders")
    public List<Order> getCustomerOrders(@RequestParam String customerID) {
        return customerOrderService.getCustomerOrders(customerID);
    }

    @GetMapping("/getCustomerCurrentOrder")
    public Order getCustomerCurrentOrder(@RequestParam String customerID) {
        return customerOrderService.getCustomerCurrentOrder(customerID);
    }
}
