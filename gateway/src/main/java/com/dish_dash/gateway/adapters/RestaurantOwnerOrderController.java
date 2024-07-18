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
  public boolean updateOrderStatusByRestaurantOwner(
      @RequestParam Long orderID, OrderStatus orderStatus) {
    return restaurantOwnerOrderApi.updateOrderStatusByRestaurantOwner(orderID, orderStatus);
  }

  @GetMapping("/orderHistory")
  @Authentication
  List<OrderDto> getRestaurantOwnerOrderHistory(String username) {
    return restaurantOwnerOrderApi.getRestaurantOwnerOrderHistory(Long.valueOf(username));
  }

  @GetMapping("/activeOrders")
  @Authentication
  List<OrderDto> getRestaurantOwnerActiveOrders(String username) {
    return restaurantOwnerOrderApi.getRestaurantOwnerActiveOrders(Long.valueOf(username));
  }
}
