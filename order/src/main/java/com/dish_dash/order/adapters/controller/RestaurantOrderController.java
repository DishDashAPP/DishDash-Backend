package com.dish_dash.order.adapters.controller;

import com.dishDash.common.dto.OrderDto;
import com.dishDash.common.enums.OrderStatus;
import com.dishDash.common.feign.order.RestaurantOwnerOrderApi;
import com.dish_dash.order.application.service.RestaurantOrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order/restaurantOwner")
@RequiredArgsConstructor
public class RestaurantOrderController implements RestaurantOwnerOrderApi {
  private final RestaurantOrderService restaurantOrderService;

  @Override
  public boolean updateOrderStatusByRestaurantOwner(long orderID, OrderStatus orderStatus) {
    return restaurantOrderService.updateOrderStatusByRestaurantOwner(orderID, orderStatus);
  }

  @Override
  public List<OrderDto> getRestaurantOwnerOrderHistory(long restaurantOwnerID) {
    return restaurantOrderService.getRestaurantOwnerOrderHistory(restaurantOwnerID);
  }

  @Override
  public List<OrderDto> getRestaurantOwnerActiveOrders(long restaurantOwnerID) {
    return restaurantOrderService.getRestaurantOwnerActiveOrders(restaurantOwnerID);
  }
}
