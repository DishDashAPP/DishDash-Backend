package com.dish_dash.order.adapters.controller;

import com.dish_dash.order.application.service.OrderService;
import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController implements OrderApi {

  private final OrderService orderService;

  @Override
  public Order viewOrder(@RequestParam Long orderID) {
    return orderService.viewOrder(orderID);
  }

  @Override
  public OrderStatus getOrderStatus(@RequestParam Long orderID) {
    return orderService.getOrderStatus(orderID);
  }

  @Override
  public boolean prepareOrder(@RequestParam Order order) {
    return orderService.prepareOrder(order);
  }
}
