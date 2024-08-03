package com.dish_dash.gateway.adapters;

import com.dishDash.common.dto.CustomerDto;
import com.dishDash.common.dto.FoodViewDto;
import com.dishDash.common.dto.RestaurantOwnerDto;
import com.dishDash.common.dto.ReviewDto;
import com.dishDash.common.feign.Product.FoodApi;
import com.dishDash.common.feign.order.ReviewApi;
import com.dishDash.common.feign.user.UserApi;
import com.dish_dash.gateway.annotation.Authentication;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
  private final UserApi userApi;
  private final FoodApi foodApi;
  private final ReviewApi reviewApi;

  @PutMapping
  @Authentication(userId = "#userId")
  Boolean modifyCustomerProfile(Long userId, @RequestBody CustomerDto customerDto) {
    return userApi.modifyCustomerProfile(userId, customerDto);
  }

  @GetMapping()
  @Authentication(userId = "#userId")
  public CustomerDto getUserProfile(Long userId) {
    return userApi.getCustomerProfile(userId);
  }

  @GetMapping("/restaurant")
  @Authentication
  public RestaurantOwnerDto getRestaurantProfile(@RequestParam Long restaurantId) {
    return userApi.getRestaurantOwnerProfile(restaurantId);
  }

  @GetMapping("/food/{id}")
  @Authentication
  public FoodViewDto getFoodById(@PathVariable long id) {
    return foodApi.getFoodById(id);
  }

  @GetMapping("/food/{restaurantId}")
  @Authentication
  public List<ReviewDto> getRestaurantReview(@PathVariable long restaurantId) {
    return reviewApi.getRestaurantReview(restaurantId);
  }
}
