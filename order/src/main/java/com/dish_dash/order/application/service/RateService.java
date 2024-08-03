package com.dish_dash.order.application.service;

import com.dishDash.common.dto.RateDto;
import com.dishDash.common.dto.RestaurantCommentsDto;
import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.model.Rate;
import com.dish_dash.order.domain.repository.RateRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateService {

  private final RateRepository rateRepository;
  private final OrderService orderService;

  public boolean setOrderRate(long customerId, long orderId, int point) {
    Rate rate = Rate.builder().customerId(customerId).orderId(orderId).point(point).build();
    return rateRepository.save(rate).getId() > 0;
  }

  public boolean setDeliveryRate(long customerId, long orderId, int point) {
    Rate rate = Rate.builder().customerId(customerId).orderId(orderId).point(point).build();
    return rateRepository.save(rate).getId() > 0;
  }

  public RateDto getDeliveryRate(String deliveryPersonID) {
    List<Rate> rateOptional = rateRepository.findAllByDeliveryPersonID(deliveryPersonID);
    long sum = 0L;
    int numberOfRates = 0;
    if (rateOptional.isEmpty())
      return RateDto.builder().numberOfRate(numberOfRates).rate(sum).build();
    for (Rate rate : rateOptional) {
      sum += rate.getPoint();
      numberOfRates++;
    }
    return RateDto.builder()
        .numberOfRate(numberOfRates)
        .rate(numberOfRates == 0 ? 0 : (float) sum / numberOfRates)
        .build();
  }

  public RestaurantCommentsDto getRestaurantComments(long restaurantId) {
    long sumRate = 0;
    int numberOfRates = 0;
    int numberOfReviews = 0;
    List<Order> orders = orderService.getAllOrderByRestaurantOwnerId(restaurantId);
    for (Order order : orders) {
      if (Objects.nonNull(order.getRestaurantRate())) {
        sumRate += order.getRestaurantRate().getPoint();
        numberOfRates++;
      }
      if (Objects.nonNull(order.getReview())) numberOfReviews++;
    }
    float avgRate = numberOfRates == 0 ? 0 : (float) sumRate / numberOfRates;
    return RestaurantCommentsDto.builder()
        .avg(avgRate)
        .numberOfRate(numberOfRates)
        .numberOfReview(numberOfReviews)
        .build();
  }
}
