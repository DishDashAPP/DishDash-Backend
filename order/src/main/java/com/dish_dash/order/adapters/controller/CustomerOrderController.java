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
public class CustomerOrderController implements CustomerOrderAPi {

  private final CustomerOrderService customerOrderService;

  @Override
  public Order createOrder(
          @RequestParam Long customerId,
          @RequestParam Long restaurantOwnerId,
          @RequestBody List<OrderItem> orderItems) {
    return customerOrderService.createOrder(customerId, restaurantOwnerId, orderItems);
  }

  @Override
  public Order modifyOrder(@RequestParam Long orderId, @RequestBody List<OrderItem> orderItems) {
    return customerOrderService.modifyOrder(orderId, orderItems);
  }

  @Override
  public boolean setOrderRate(
          @RequestParam Long customerId, @RequestParam Long orderId, @RequestParam int point) {
    return customerOrderService.setOrderRate(customerId, orderId, point);
  }

  @Override
  public boolean setDeliveryRate(
          @RequestParam Long customerId, @RequestParam Long orderId, @RequestParam int point) {
    return customerOrderService.setDeliveryRate(customerId, orderId, point);
  }

  @Override
  public List<Order> getCustomerOrders(@RequestParam Long customerId) {
    return customerOrderService.getCustomerOrders(customerId);
  }

  @Override
  public Order getCustomerCurrentOrder(@RequestParam Long customerId) {
    return customerOrderService.getCustomerCurrentOrder(customerId);
  }
}
