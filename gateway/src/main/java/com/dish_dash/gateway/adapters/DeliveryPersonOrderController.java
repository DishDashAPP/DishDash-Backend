package com.dish_dash.gateway.adapters;

import com.dishDash.common.dto.OrderDto;
import com.dishDash.common.enums.OrderStatus;
import com.dishDash.common.feign.order.DeliveryPersonOrderApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order/deliveryPerson")
@RequiredArgsConstructor
public class DeliveryPersonOrderController {
  private final DeliveryPersonOrderApi deliveryPersonOrderApi;

  @PutMapping("/status")
  public boolean updateOrderStatusByDeliveryPerson(
      @RequestParam Long orderID, @RequestParam OrderStatus status) {
    return deliveryPersonOrderApi.updateOrderStatusByDeliveryPerson(orderID, status);
  }

  @GetMapping("/status")
  public OrderDto getDeliveryPersonCurrentOrder(@RequestParam String deliveryPersonID) {
    return deliveryPersonOrderApi.getDeliveryPersonCurrentOrder(deliveryPersonID);
  }
}
