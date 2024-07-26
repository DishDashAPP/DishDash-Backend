package com.dish_dash.gateway.adapters;

import com.dishDash.common.dto.OrderDto;
import com.dishDash.common.enums.OrderStatus;
import com.dishDash.common.feign.order.RestaurantOwnerOrderApi;
import com.dish_dash.gateway.annotation.Authentication;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/order/restaurantOwner")
@RequiredArgsConstructor
public class RestaurantOwnerOrderController {
  private final RestaurantOwnerOrderApi restaurantOwnerOrderApi;

  @PutMapping("/status")
  @Authentication(userId = "#userId")
  public boolean updateOrderStatusByRestaurantOwner(
      Long userId, @RequestParam long orderId, @RequestParam OrderStatus status) {
    return restaurantOwnerOrderApi.updateOrderStatusByRestaurantOwner(userId, orderId, status);
  }

  @GetMapping("/orderHistory")
  @Authentication(userId = "#userId")
  List<OrderDto> getRestaurantOwnerOrderHistory(Long userId) {
    return restaurantOwnerOrderApi.getRestaurantOwnerOrderHistory(userId);
  }

  @GetMapping("/activeOrders")
  @Authentication(userId = "#userId")
  List<OrderDto> getRestaurantOwnerActiveOrders(Long userId) {
    return restaurantOwnerOrderApi.getRestaurantOwnerActiveOrders(userId);
  }
}
