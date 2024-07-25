package com.dish_dash.order.adapters.controller;

import com.dishDash.common.dto.OrderDto;
import com.dishDash.common.dto.OrderItemCreateDto;
import com.dishDash.common.dto.OrderItemDto;
import com.dishDash.common.feign.order.CustomerOrderAPi;
import com.dish_dash.order.application.service.CustomerOrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order/customer")
public class CustomerOrderController implements CustomerOrderAPi {

  private final CustomerOrderService customerOrderService;

  @Override
  public OrderDto createOrder(
      long customerId, long restaurantOwnerId, List<OrderItemCreateDto> orderItemsDto) {
    return customerOrderService.createOrder(customerId, restaurantOwnerId, orderItemsDto);
  }

  @Override
  public OrderDto modifyOrder(long orderId, List<OrderItemCreateDto> orderItemsDto) {
    return customerOrderService.modifyOrder(orderId, orderItemsDto);
  }

  @Override
  public boolean setOrderRate(long customerId, long orderId, int point) {
    return customerOrderService.setOrderRate(customerId, orderId, point);
  }

  @Override
  public boolean setDeliveryRate(long customerId, long orderId, int point) {
    return customerOrderService.setDeliveryRate(customerId, orderId, point);
  }

  @Override
  public List<OrderDto> getCustomerOrders(long customerId) {
    return customerOrderService.getCustomerOrders(customerId);
  }

  @Override
  public OrderDto getCustomerCurrentOrder(long customerId) {
    return customerOrderService.getCustomerCurrentOrder(customerId);
  }
}
