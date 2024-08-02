package com.dishDash.common.feign.order;

import com.dishDash.common.dto.ShoppingCartDto;
import com.dishDash.common.dto.ShoppingCartItemCreateDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "order-service", contextId = "customer-shopping-cart-service", path = "/shoppingCart/customer")
public interface CustomerShoppingCartAPi {
  @PostMapping("/createShoppingCart")
  ShoppingCartDto createShoppingCart(
      @RequestParam Long customerId,
      @RequestParam Long restaurantOwnerId);

  @PostMapping("/modifyShoppingCart")
  ShoppingCartDto modifyOrder(
      @RequestParam Long customerId,
      @RequestParam Long orderId,
      @RequestBody List<ShoppingCartItemCreateDto> shoppingCartItemsCreateDto);

  @GetMapping("/getCustomerShoppingCarts")
  List<ShoppingCartDto> getCustomerShoppingCarts(@RequestParam Long customerId);
}
