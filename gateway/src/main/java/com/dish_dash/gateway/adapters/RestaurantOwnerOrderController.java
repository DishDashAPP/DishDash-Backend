package com.dish_dash.gateway.adapters;

import com.dishDash.common.dto.OrderDto;
import com.dishDash.common.enums.OrderStatus;
import com.dishDash.common.feign.order.RestaurantOwnerOrderApi;
import com.dish_dash.gateway.annotation.Authentication;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order/restaurantOwner")
@RequiredArgsConstructor
public class RestaurantOwnerOrderController {
  private final RestaurantOwnerOrderApi restaurantOwnerOrderApi;

  @PostMapping("/status")
  @Authentication
  public boolean updateOrderStatusByRestaurantOwner(
      @RequestParam Long orderId, @RequestParam OrderStatus status) {
    return restaurantOwnerOrderApi.updateOrderStatusByRestaurantOwner(orderId, status);
  }

  @GetMapping("/orderHistory")
  @Authentication
  List<OrderDto> getRestaurantOwnerOrderHistory(long userId) {
    return restaurantOwnerOrderApi.getRestaurantOwnerOrderHistory(userId);
  }

  @GetMapping("/activeOrders")
  @Authentication
  List<OrderDto> getRestaurantOwnerActiveOrders(long userId) {
    return restaurantOwnerOrderApi.getRestaurantOwnerActiveOrders(userId);
  }
}
