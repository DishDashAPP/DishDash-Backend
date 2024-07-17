package com.dish_dash.order.adapters.controller;

import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.model.OrderStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "delivery-person-order-service")

public interface DeliveryPersonOrderApi {
    boolean updateOrderStatusByDeliveryPerson(
            @RequestParam Long orderID, @RequestParam OrderStatus status);

    Order getDeliveryPersonCurrentOrder(@RequestParam String deliveryPersonID);
}
