package com.dish_dash.order.application.service;

import com.dishDash.common.Price;
import com.dishDash.common.dto.FoodViewDto;
import com.dishDash.common.dto.OrderDto;
import com.dishDash.common.dto.OrderItemCreateDto;
import com.dishDash.common.enums.ErrorCode;
import com.dishDash.common.enums.OrderStatus;
import com.dishDash.common.exception.CustomException;
import com.dishDash.common.exception.MoreThanOneOrderException;
import com.dishDash.common.feign.Product.FoodApi;
import com.dish_dash.order.domain.mapper.OrderMapper;
import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.model.OrderItem;
import com.dish_dash.order.domain.model.Rate;
import com.dish_dash.order.domain.repository.OrderItemRepository;
import com.dish_dash.order.domain.repository.OrderRepository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerOrderService {

  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;
  private final FoodApi foodApi;

  @Transactional
  public OrderDto createOrder(
      long customerId, long restaurantOwnerId, List<OrderItemCreateDto> orderItemsDto) {
    if (orderRepository.findByCustomerIdAndStatusNot(customerId, OrderStatus.DELIVERED).isPresent())
      throw new MoreThanOneOrderException(
          ErrorCode.BAD_REQUEST, "There is an active order for the customer");
    AtomicReference<Double> totalPrice = new AtomicReference<>(0.0);

    // Calculate the total price for the order items
    List<OrderItem> orderItems =
        orderItemsDto.stream()
            .map(
                dto -> {
                  OrderItem orderItem = OrderMapper.INSTANCE.orderItemCreatenDtoToOrderItem(dto);

                  // Fetch the food details using FoodApi
                  FoodViewDto food = foodApi.getFoodById(dto.getFoodId());

                  // Calculate the total price for the order item
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

    // Create the order with the calculated total price
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

    // Save the order to get the generated ID
    order = orderRepository.save(order);
    final Order savedOrder = order;

    // Associate OrderItems with the saved Order
    orderItems.forEach(orderItem -> orderItem.setOrder(savedOrder));

    // Save order items
    orderItemRepository.saveAll(orderItems);

    // Update order with order items
    order.setOrderItems(orderItems);
    orderRepository.save(order);

    return OrderMapper.INSTANCE.orderToDto(order);
  }

  @Transactional
  public OrderDto modifyOrder(
      long customerId, long orderId, List<OrderItemCreateDto> orderItemsDto) {
    Optional<Order> orderOptional = orderRepository.findById(orderId);
    if (orderOptional.isPresent()) {
      Order order = orderOptional.get();
      if (customerId != order.getCustomerId())
        throw new CustomException(ErrorCode.BAD_REQUEST, "Customer id mismatch");

      AtomicReference<Double> totalPrice = new AtomicReference<>(0.0);
      List<OrderItem> orderItems =
          orderItemsDto.stream()
              .map(
                  dto -> {
                    OrderItem orderItem = OrderMapper.INSTANCE.orderItemCreateDtoToOrderItem(dto);
                    orderItem.setOrder(order);

                    // Fetch the food details using FoodApi
                    FoodViewDto food = foodApi.getFoodById(dto.getFoodId());

                    // Calculate the total price for the order item
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

      order.setOrderItems(orderItems);
      order.setTotalPrice(
          Price.builder()
              .amount(totalPrice.get())
              .unit(orderItems.get(0).getPrice().getUnit())
              .build());
      return OrderMapper.INSTANCE.orderToDto(orderRepository.save(order));
    }
    return null;
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
