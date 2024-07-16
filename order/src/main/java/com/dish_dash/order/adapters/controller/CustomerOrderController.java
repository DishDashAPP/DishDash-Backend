package com.dish_dash.order.adapters.controller;

import com.dish_dash.order.application.service.CustomerOrderService;
import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.model.OrderItem;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customerOrder")
@RequiredArgsConstructor
public class CustomerOrderController {

  private final CustomerOrderService customerOrderService;

  @PostMapping("/createOrder")
  public Order createOrder(
      @RequestParam Long customerId,
      @RequestParam Long restaurantOwnerId,
      @RequestBody List<OrderItem> orderItems) {
    return customerOrderService.createOrder(customerId, restaurantOwnerId, orderItems);
  }

  @PostMapping("/modifyOrder")
  public Order modifyOrder(@RequestParam Long orderId, @RequestBody List<OrderItem> orderItems) {
    return customerOrderService.modifyOrder(orderId, orderItems);
  }

  @PostMapping("/setOrderRate")
  public boolean setOrderRate(
      @RequestParam Long customerId, @RequestParam Long orderId, @RequestParam int point) {
    return customerOrderService.setOrderRate(customerId, orderId, point);
  }

  @PostMapping("/setDeliveryRate")
  public boolean setDeliveryRate(
      @RequestParam Long customerId, @RequestParam Long orderId, @RequestParam int point) {
    return customerOrderService.setDeliveryRate(customerId, orderId, point);
  }

  @GetMapping("/getCustomerOrders")
  public List<Order> getCustomerOrders(@RequestParam Long customerId) {
    return customerOrderService.getCustomerOrders(customerId);
  }

  @GetMapping("/getCustomerCurrentOrder")
  public Order getCustomerCurrentOrder(@RequestParam Long customerId) {
    return customerOrderService.getCustomerCurrentOrder(customerId);
  }
}
