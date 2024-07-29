package com.dishDash.common.feign.order;

import com.dishDash.common.dto.OrderDto;
import com.dishDash.common.enums.OrderStatus;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
    name = "order-service",
    contextId = "restaurant-owner-order-service",
    path = "/order/restaurantOwner")
public interface RestaurantOwnerOrderApi {
  @PostMapping("/status")
  boolean updateOrderStatusByRestaurantOwner(
      @RequestParam Long restaurantOwnerId, @RequestParam long orderID, @RequestParam OrderStatus orderStatus);

  @GetMapping("/orderHistory")
  List<OrderDto> getRestaurantOwnerOrderHistory(@RequestParam Long restaurantOwnerId);

  @GetMapping("/activeOrders")
  List<OrderDto> getRestaurantOwnerActiveOrders(@RequestParam Long restaurantOwnerId);
}
