package com.dish_dash.order.adapters.controller;

import com.dish_dash.order.application.service.OrderService;
import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @GetMapping("/viewOrder")
  public Order viewOrder(@RequestParam Long orderID) {
    return orderService.viewOrder(orderID);
  }

  @GetMapping("/getOrderStatus")
  public OrderStatus getOrderStatus(@RequestParam Long orderID) {
    return orderService.getOrderStatus(orderID);
  }

  @PostMapping("/prepareOrder")
  public boolean prepareOrder(@RequestParam Order order) {
    return orderService.prepareOrder(order);
  }
}
