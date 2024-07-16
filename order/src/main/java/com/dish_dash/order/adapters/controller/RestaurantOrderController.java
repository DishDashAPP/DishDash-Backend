package com.dish_dash.order.adapters.controller;

import com.dish_dash.order.application.service.RestaurantOrderService;
import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.model.OrderStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurantOrder")
@RequiredArgsConstructor
public class RestaurantOrderController {

  private final RestaurantOrderService restaurantOrderService;

  @PostMapping("/updateOrderStatus")
  public boolean updateOrderStatusByRestaurantOwner(
      @RequestParam Long orderID, OrderStatus orderStatus) {
    return restaurantOrderService.updateOrderStatusByRestaurantOwner(orderID, orderStatus);
  }

  @GetMapping("/getOrderHistory")
  public List<Order> getRestaurantOwnerOrderHistory(@RequestParam Long restaurantOwnerID) {
    return restaurantOrderService.getRestaurantOwnerOrderHistory(restaurantOwnerID);
  }

  @GetMapping("/getActiveOrders")
  public List<Order> getRestaurantOwnerActiveOrders(@RequestParam Long restaurantOwnerID) {
    return restaurantOrderService.getRestaurantOwnerActiveOrders(restaurantOwnerID);
  }
}
