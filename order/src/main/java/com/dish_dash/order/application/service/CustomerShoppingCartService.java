package com.dish_dash.order.application.service;

import com.dishDash.common.Price;
import com.dishDash.common.dto.FoodViewDto;
import com.dishDash.common.dto.ShoppingCartDto;
import com.dishDash.common.dto.ShoppingCartItemCreateDto;
import com.dishDash.common.enums.CurrencyUnit;
import com.dishDash.common.enums.ErrorCode;
import com.dishDash.common.exception.CustomException;
import com.dishDash.common.feign.Product.FoodApi;
import com.dishDash.common.feign.user.UserApi;
import com.dish_dash.order.domain.mapper.ShoppingCartMapper;
import com.dish_dash.order.domain.model.ShoppingCart;
import com.dish_dash.order.domain.model.ShoppingCartItem;
import com.dish_dash.order.domain.repository.ShoppingCartRepository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerShoppingCartService {
  private final ShoppingCartRepository shoppingCartRepository;
  private final FoodApi foodApi;
  private final UserApi userApi;

  public ShoppingCartDto createShoppingCart(long customerId, long restaurantOwnerId) {
    Optional<ShoppingCart> shoppingCartOptional =
        shoppingCartRepository.findByCustomerIdAndRestaurantOwnerId(customerId, restaurantOwnerId);

    if (shoppingCartOptional.isPresent())
      return ShoppingCartMapper.INSTANCE.shoppingCartToDto(shoppingCartOptional.get());

    ShoppingCart shoppingCart =
        shoppingCartRepository.save(
            ShoppingCart.builder()
                .customerId(customerId)
                .restaurantOwnerId(restaurantOwnerId)
                .totalPrice(Price.builder().build())
                .build());

    ShoppingCartDto shoppingCartDto = ShoppingCartMapper.INSTANCE.shoppingCartToDto(shoppingCart);
    if (shoppingCartDto.getRestaurantOwnerId() != 0) {
      shoppingCartDto.setRestaurantOwner(
          userApi.getRestaurantOwnerProfile(shoppingCartDto.getRestaurantOwnerId()));
    }

    return shoppingCartDto;
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
              .toList();
      shoppingCart.getShoppingCartItems().clear();
      shoppingCart.getShoppingCartItems().addAll(orderItems);
      shoppingCart.setTotalPrice(
          Price.builder().amount(totalPrice.get()).unit(CurrencyUnit.TOMAN).build());
      ShoppingCartDto shoppingCartDto =
          ShoppingCartMapper.INSTANCE.shoppingCartToDto(shoppingCartRepository.save(shoppingCart));
      log.info("Shopping cart updated. {}", shoppingCartDto);
      shoppingCartDto
          .getShoppingCartItems()
          .forEach(
              (item -> {
                FoodViewDto foodDto = foodApi.getFoodById(item.getFoodId());
                log.info("Shopping cart item updated. item: {}, food: {}", item, foodDto.getName());
                item.setName(foodDto.getName());
                item.setDescription(foodDto.getDescription());
              }));
      log.info("Shopping cart Add name. {}", shoppingCartDto);
      if (shoppingCartDto.getRestaurantOwnerId() != 0) {
        shoppingCartDto.setRestaurantOwner(
            userApi.getRestaurantOwnerProfile(shoppingCartDto.getRestaurantOwnerId()));
      }
      return shoppingCartDto;
    }
    return null;
  }

  public List<ShoppingCartDto> getCustomerShoppingCarts(long customerId) {
    return shoppingCartRepository.findByCustomerId(customerId).stream()
        .map(
            shoppingCart -> {
              ShoppingCartDto shoppingCartDto =
                  ShoppingCartMapper.INSTANCE.shoppingCartToDto(shoppingCart);

              shoppingCartDto.setShoppingCartItems(
                  shoppingCartDto.getShoppingCartItems().stream()
                      .peek(
                          item -> {
                            FoodViewDto foodDto = foodApi.getFoodById(item.getFoodId());
                            item.setName(foodDto.getName());
                            item.setDescription(foodDto.getDescription());
                          })
                      .collect(Collectors.toList()));
              if (shoppingCartDto.getRestaurantOwnerId() != 0) {
                shoppingCartDto.setRestaurantOwner(
                    userApi.getRestaurantOwnerProfile(shoppingCartDto.getRestaurantOwnerId()));
              }
              return shoppingCartDto;
            })
        .collect(Collectors.toList());
  }
}
