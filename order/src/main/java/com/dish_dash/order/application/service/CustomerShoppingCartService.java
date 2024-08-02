package com.dish_dash.order.application.service;

import com.dishDash.common.Price;
import com.dishDash.common.dto.FoodViewDto;
import com.dishDash.common.dto.ShoppingCartDto;
import com.dishDash.common.dto.ShoppingCartItemCreateDto;
import com.dishDash.common.enums.ErrorCode;
import com.dishDash.common.exception.CustomException;
import com.dishDash.common.feign.Product.FoodApi;
import com.dish_dash.order.domain.mapper.ShoppingCartMapper;
import com.dish_dash.order.domain.model.ShoppingCart;
import com.dish_dash.order.domain.model.ShoppingCartItem;
import com.dish_dash.order.domain.repository.ShoppingCartItemRepository;
import com.dish_dash.order.domain.repository.ShoppingCartRepository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerShoppingCartService {

  private final ShoppingCartRepository shoppingCartRepository;
  private final FoodApi foodApi;
  private final ShoppingCartItemRepository shoppingCartItemRepository;

  @Transactional
  public ShoppingCartDto createShoppingCart(
      long customerId, long restaurantOwnerId, List<ShoppingCartItemCreateDto> orderItemsDto) {
    AtomicReference<Double> totalPrice = new AtomicReference<>(0.0);

    List<ShoppingCartItem> orderItems =
        orderItemsDto.stream()
            .map(
                dto -> {
                  ShoppingCartItem orderItem =
                      ShoppingCartMapper.INSTANCE.shoppingCartItemCreationDtoToShoppingCartItem(
                          dto);

                  FoodViewDto food = foodApi.getFoodById(dto.getFoodId());

                  double itemTotalPrice = food.getPrice().getAmount() * dto.getQuantity();
                  orderItem.setPrice(
                      Price.builder()
                          .amount(itemTotalPrice)
                          .unit(food.getPrice().getUnit())
                          .build());
                  totalPrice.updateAndGet(v -> v + itemTotalPrice);
                  return orderItem;
                })
            .collect(Collectors.toList());

    var shoppingCart =
        ShoppingCart.builder()
            .customerId(customerId)
            .restaurantOwnerId(restaurantOwnerId)
            .totalPrice(
                Price.builder()
                    .amount(totalPrice.get())
                    .unit(orderItems.get(0).getPrice().getUnit())
                    .build())
            .build();

    shoppingCart = shoppingCartRepository.save(shoppingCart);
    final var savedShoppingCart = shoppingCart;

    orderItems.forEach(orderItem -> orderItem.setShoppingCart(savedShoppingCart));

    shoppingCartItemRepository.saveAll(orderItems);

    shoppingCart.setShoppingCartItems(orderItems);
    shoppingCartRepository.save(shoppingCart);

    return ShoppingCartMapper.INSTANCE.shoppingCartToDto(shoppingCart);
  }

  @Transactional
  public ShoppingCartDto modifyShoppingCart(
      long customerId, long shoppingCartId, List<ShoppingCartItemCreateDto> orderItemsDto) {
    Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findById(shoppingCartId);
    if (shoppingCartOptional.isPresent()) {
      var shoppingCart = shoppingCartOptional.get();
      if (customerId != shoppingCart.getCustomerId())
        throw new CustomException(ErrorCode.BAD_REQUEST, "Customer id mismatch");

      AtomicReference<Double> totalPrice = new AtomicReference<>(0.0);
      List<ShoppingCartItem> orderItems =
          orderItemsDto.stream()
              .map(
                  dto -> {
                    ShoppingCartItem orderItem =
                        ShoppingCartMapper.INSTANCE.shoppingCartItemCreationDtoToShoppingCartItem(
                            dto);
                    orderItem.setShoppingCart(shoppingCart);

                    FoodViewDto food = foodApi.getFoodById(dto.getFoodId());

                    double itemTotalPrice = food.getPrice().getAmount() * dto.getQuantity();
                    orderItem.setPrice(
                        Price.builder()
                            .amount(itemTotalPrice)
                            .unit(food.getPrice().getUnit())
                            .build());
                    totalPrice.updateAndGet(v -> v + itemTotalPrice);

                    return orderItem;
                  })
              .collect(Collectors.toList());

      shoppingCart.setShoppingCartItems(orderItems);
      shoppingCart.setTotalPrice(
          Price.builder()
              .amount(totalPrice.get())
              .unit(orderItems.get(0).getPrice().getUnit())
              .build());
      return ShoppingCartMapper.INSTANCE.shoppingCartToDto(
          shoppingCartRepository.save(shoppingCart));
    }
    return null;
  }

  public List<ShoppingCartDto> getCustomerShoppingCarts(long customerID) {
    return shoppingCartRepository.findByCustomerId(customerID).stream()
        .map(ShoppingCartMapper.INSTANCE::shoppingCartToDto)
        .collect(Collectors.toList());
  }
}
