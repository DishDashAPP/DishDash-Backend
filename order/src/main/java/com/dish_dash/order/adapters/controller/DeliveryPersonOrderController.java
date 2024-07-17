package com.dish_dash.order.adapters.controller;

import com.dish_dash.order.application.service.DeliveryPersonOrderService;
import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deliveryPersonOrder")
@RequiredArgsConstructor
public class DeliveryPersonOrderController implements DeliveryPersonOrderApi {

  private final DeliveryPersonOrderService deliveryPersonOrderService;

  @Override
  public boolean updateOrderStatusByDeliveryPerson(
          @RequestParam Long orderID, @RequestParam OrderStatus status) {
    return deliveryPersonOrderService.updateOrderStatusByDeliveryPerson(orderID, status);
  }

  @Override
  public Order getDeliveryPersonCurrentOrder(@RequestParam String deliveryPersonID) {
    return deliveryPersonOrderService.getDeliveryPersonCurrentOrder(deliveryPersonID);
  }
}
