package com.dish_dash.order.adapters.controller;

import com.dishDash.common.dto.OrderDto;
import com.dishDash.common.enums.OrderStatus;
import com.dishDash.common.feign.order.OrderApi;
import com.dish_dash.order.application.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController implements OrderApi {
  private final OrderService orderService;

  @Override
  public OrderDto viewOrder(Long orderID) {
    return orderService.viewOrder(orderID);
  }

  @Override
  public OrderStatus getOrderStatus(Long orderID) {
    return orderService.getOrderStatus(orderID);
  }

  @Override
  public boolean prepareOrder(OrderDto order) {
    return orderService.prepareOrder(order);
  }
}
