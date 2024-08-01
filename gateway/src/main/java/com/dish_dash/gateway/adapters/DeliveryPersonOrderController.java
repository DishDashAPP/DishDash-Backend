package com.dish_dash.gateway.adapters;

import com.dishDash.common.dto.OrderDto;
import com.dishDash.common.enums.OrderStatus;
import com.dishDash.common.feign.order.DeliveryPersonOrderApi;
import com.dish_dash.gateway.annotation.Authentication;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/order/deliveryPerson")
@RequiredArgsConstructor
public class DeliveryPersonOrderController {
  private final DeliveryPersonOrderApi deliveryPersonOrderApi;

  @PutMapping("/status")
  @Authentication(userId = "#userId")
  public boolean updateOrderStatusByDeliveryPerson(Long orderId, @RequestParam OrderStatus status) {
    return deliveryPersonOrderApi.updateOrderStatusByDeliveryPerson(orderId, status);
  }

  @GetMapping("/current")
  @Authentication(userId = "#userId")
  public OrderDto getDeliveryPersonCurrentOrder(Long userId) {
    return deliveryPersonOrderApi.getDeliveryPersonCurrentOrder(userId);
  }
}
