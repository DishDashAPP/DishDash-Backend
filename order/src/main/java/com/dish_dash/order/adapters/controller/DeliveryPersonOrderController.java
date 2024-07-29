package com.dish_dash.order.adapters.controller;

import com.dishDash.common.dto.OrderDto;
import com.dishDash.common.enums.OrderStatus;
import com.dishDash.common.feign.order.DeliveryPersonOrderApi;
import com.dish_dash.order.application.service.DeliveryPersonOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order/deliveryPerson")
public class DeliveryPersonOrderController implements DeliveryPersonOrderApi {

  private final DeliveryPersonOrderService deliveryPersonOrderService;

  @Override
  public boolean updateOrderStatusByDeliveryPerson(Long orderID, OrderStatus status) {
    return deliveryPersonOrderService.updateOrderStatusByDeliveryPerson(orderID, status);
  }

  @Override
  public OrderDto getDeliveryPersonCurrentOrder(Long deliveryPersonID) {
    return deliveryPersonOrderService.getDeliveryPersonCurrentOrder(deliveryPersonID);
  }
}
