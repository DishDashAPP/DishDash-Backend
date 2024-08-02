package com.dish_dash.order.application.service;

import com.dishDash.common.Price;
import com.dishDash.common.dto.FoodViewDto;
import com.dishDash.common.dto.OrderDto;
import com.dishDash.common.dto.TransactionDto;
import com.dishDash.common.enums.ErrorCode;
import com.dishDash.common.enums.OrderStatus;
import com.dishDash.common.exception.CustomException;
import com.dishDash.common.exception.MoreThanOneOrderException;
import com.dishDash.common.feign.Product.FoodApi;
import com.dishDash.common.feign.payment.PaymentApi;
import com.dish_dash.order.domain.mapper.OrderMapper;
import com.dish_dash.order.domain.model.*;
import com.dish_dash.order.domain.repository.OrderItemRepository;
import com.dish_dash.order.domain.repository.OrderRepository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import com.dish_dash.order.domain.repository.ShoppingCartItemRepository;
import com.dish_dash.order.domain.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerOrderService {

  private final ShoppingCartRepository shoppingCartRepository;
  private final ShoppingCartItemRepository shoppingCartItemRepository;
  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;
  private final FoodApi foodApi;
  private final PaymentApi paymentApi;

  @Transactional
  public TransactionDto createOrder(long customerId, long restaurantOwnerId) {
    if (orderRepository.findByCustomerIdAndStatusNot(customerId, OrderStatus.DELIVERED).isPresent())
      throw new MoreThanOneOrderException(
          ErrorCode.BAD_REQUEST, "There is an active order for the customer");

    ShoppingCart shoppingCart =
        shoppingCartRepository
            .findByCustomerIdAndRestaurantOwnerId(customerId, restaurantOwnerId)
            .stream()
            .findFirst()
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "Shopping cart not found"));

    List<ShoppingCartItem> shoppingCartItems =
        shoppingCartItemRepository.findByShoppingCartId(shoppingCart.getId());

    AtomicReference<Double> totalPrice = new AtomicReference<>(0.0);
    List<OrderItem> orderItems =
        shoppingCartItems.stream()
            .map(
                cartItem -> {
                  OrderItem orderItem = new OrderItem();

                  FoodViewDto food = foodApi.getFoodById(cartItem.getFoodId());

                  double itemTotalPrice = food.getPrice().getAmount() * cartItem.getQuantity();
                  orderItem.setPrice(
                      Price.builder()
                          .amount(itemTotalPrice)
                          .unit(food.getPrice().getUnit())
                          .build());
                  totalPrice.updateAndGet(v -> v + itemTotalPrice);

                  orderItem.setFoodId(cartItem.getFoodId());
                  orderItem.setQuantity(cartItem.getQuantity());

                  return orderItem;
                })
            .collect(Collectors.toList());

    Order order =
        Order.builder()
            .customerId(customerId)
            .restaurantOwnerId(restaurantOwnerId)
            .status(OrderStatus.PREPARING)
            .totalPrice(
                Price.builder()
                    .amount(totalPrice.get())
                    .unit(orderItems.get(0).getPrice().getUnit())
                    .build())
            .build();

    order = orderRepository.save(order);
    final Order savedOrder = order;
    orderItems.forEach(orderItem -> orderItem.setOrder(savedOrder));
    orderItemRepository.saveAll(orderItems);

    order.setOrderItems(orderItems);
    orderRepository.save(order);

    TransactionDto transactionDto =
        paymentApi.createOrderTransaction(shoppingCart.getId(), totalPrice.get());

    shoppingCartItemRepository.deleteAll(shoppingCartItems);
    shoppingCartRepository.delete(shoppingCart);

    return transactionDto;
  }

  public boolean setOrderRate(long customerId, long orderID, int point) {
    Optional<Order> orderOptional = orderRepository.findById(orderID);
    if (orderOptional.isPresent()) {
      orderRepository.save(orderOptional.get());
      return true;
    }
    return false;
  }

  public boolean setDeliveryRate(long customerID, long orderID, int point) {
    Optional<Order> order = orderRepository.findById(orderID);
    if (order.isPresent()) {
      order.get().setDeliveryPersonRate(Rate.builder().build());
      orderRepository.save(order.get());
      return true;
    }
    return false;
  }

  public List<OrderDto> getCustomerOrders(long customerID) {
    return orderRepository.findByCustomerId(customerID).stream()
        .map(OrderMapper.INSTANCE::orderToDto)
        .collect(Collectors.toList());
  }

  public OrderDto getCustomerCurrentOrder(long customerId) {
    return orderRepository
        .findByCustomerIdAndStatusNot(customerId, OrderStatus.DELIVERED)
        .filter(
            order ->
                order.getStatus() == OrderStatus.PREPARING
                    || order.getStatus() == OrderStatus.DELIVERING)
        .map(OrderMapper.INSTANCE::orderToDto)
        .orElse(null);
  }
}
