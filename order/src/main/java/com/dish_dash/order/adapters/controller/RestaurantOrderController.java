package com.dish_dash.order.adapters.controller;

import com.dish_dash.order.application.service.RestaurantOrderService;
import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.model.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantOrder")
public class RestaurantOrderController {

    @Autowired
    private RestaurantOrderService restaurantOrderService;

    @PostMapping("/updateOrderStatus")
    public boolean updateOrderStatusByRestaurantOwner(@RequestParam String orderID, OrderStatus orderStatus) {
        return restaurantOrderService.updateOrderStatusByRestaurantOwner(orderID, orderStatus);
    }

    @GetMapping("/getOrderHistory")
    public List<Order> getRestaurantOwnerOrderHistory(@RequestParam String restaurantOwnerID) {
        return restaurantOrderService.getRestaurantOwnerOrderHistory(restaurantOwnerID);
    }

    @GetMapping("/getActiveOrders")
    public List<Order> getRestaurantOwnerActiveOrders(@RequestParam String restaurantOwnerID) {
        return restaurantOrderService.getRestaurantOwnerActiveOrders(restaurantOwnerID);
    }
}
