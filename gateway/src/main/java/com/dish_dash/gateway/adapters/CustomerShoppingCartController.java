package com.dish_dash.gateway.adapters;

import com.dishDash.common.dto.ShoppingCartDto;
import com.dishDash.common.dto.ShoppingCartItemCreateDto;
import com.dishDash.common.feign.order.CustomerShoppingCartAPi;
import com.dish_dash.gateway.annotation.Authentication;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/shoppingCart/customer")
@RequiredArgsConstructor
public class CustomerShoppingCartController {
  private final CustomerShoppingCartAPi customerShoppingCartAPi;

  @PostMapping
  @Authentication(userId = "#userId")
  public ShoppingCartDto createShoppingCart(Long userId, @RequestParam Long restaurantOwnerId) {
    return customerShoppingCartAPi.createShoppingCart(userId, restaurantOwnerId);
  }

  @PostMapping("/modifyShoppingCart")
  @Authentication(userId = "#userId")
  ShoppingCartDto modifyShoppingCart(
      Long userId,
      @RequestParam Long orderId,
      @RequestBody List<ShoppingCartItemCreateDto> orderItems) {
    return customerShoppingCartAPi.modifyOrder(userId, orderId, orderItems);
  }

  @GetMapping("/customerShoppingCarts")
  @Authentication(userId = "#userId")
  List<ShoppingCartDto> getCustomerShoppingCarts(Long userId) {
    return customerShoppingCartAPi.getCustomerShoppingCarts(userId);
  }
}
