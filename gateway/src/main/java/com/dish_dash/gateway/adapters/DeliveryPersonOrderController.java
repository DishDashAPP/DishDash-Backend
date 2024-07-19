package com.dish_dash.gateway.adapters;

import com.dishDash.common.dto.OrderDto;
import com.dishDash.common.enums.OrderStatus;
import com.dishDash.common.feign.order.DeliveryPersonOrderApi;
import com.dish_dash.gateway.annotation.Authentication;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order/deliveryPerson")
@RequiredArgsConstructor
public class DeliveryPersonOrderController {
  private final DeliveryPersonOrderApi deliveryPersonOrderApi;

  @PutMapping("/status")
  @Authentication
  public boolean updateOrderStatusByDeliveryPerson(
      @RequestParam Long orderId, @RequestParam OrderStatus status) {
    return deliveryPersonOrderApi.updateOrderStatusByDeliveryPerson(orderId, status);
  }

  @GetMapping("/current")
  @Authentication
  public OrderDto getDeliveryPersonCurrentOrder(@RequestParam String deliveryPersonId) {
    return deliveryPersonOrderApi.getDeliveryPersonCurrentOrder(deliveryPersonId);
  }
}
