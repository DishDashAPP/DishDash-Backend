package com.dish_dash.order.adapters.controller;

import com.dishDash.common.dto.ShoppingCartDto;
import com.dishDash.common.dto.ShoppingCartItemCreateDto;
import com.dishDash.common.feign.order.CustomerShoppingCartAPi;
import com.dish_dash.order.application.service.CustomerShoppingCartService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shoppingCart/customer")
public class CustomerShoppingCartController implements CustomerShoppingCartAPi {

  private final CustomerShoppingCartService customerShoppingCartService;

  @Override
  public ShoppingCartDto createShoppingCart(Long customerId, Long restaurantOwnerId) {
    return customerShoppingCartService.createShoppingCart(customerId, restaurantOwnerId);
  }

  @Override
  public ShoppingCartDto modifyOrder(
      Long customerId, Long orderId, List<ShoppingCartItemCreateDto> orderItems) {
    return customerShoppingCartService.modifyShoppingCart(customerId, orderId, orderItems);
  }

  @Override
  public List<ShoppingCartDto> getCustomerShoppingCarts(Long customerId) {
    return customerShoppingCartService.getCustomerShoppingCarts(customerId);
  }
}
