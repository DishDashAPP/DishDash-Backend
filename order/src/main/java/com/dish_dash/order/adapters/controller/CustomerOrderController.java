package com.dish_dash.order.adapters.controller;

import com.dishDash.common.dto.OrderDto;
import com.dishDash.common.dto.OrderItemCreateDto;
import com.dishDash.common.dto.TransactionDto;
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
  public TransactionDto createOrder(
          Long customerId, Long restaurantOwnerId) {
    return customerOrderService.createOrder(customerId, restaurantOwnerId);
  }

  @Override
  public boolean setOrderRate(Long customerId, Long orderId, int point) {
    return customerOrderService.setOrderRate(customerId, orderId, point);
  }

  @Override
  public boolean setDeliveryRate(Long customerId, Long orderId, int point) {
    return customerOrderService.setDeliveryRate(customerId, orderId, point);
  }

  @Override
  public List<OrderDto> getCustomerOrders(Long customerId) {
    return customerOrderService.getCustomerOrders(customerId);
  }

  @Override
  public OrderDto getCustomerCurrentOrder(Long customerId) {
    return customerOrderService.getCustomerCurrentOrder(customerId);
  }
}
