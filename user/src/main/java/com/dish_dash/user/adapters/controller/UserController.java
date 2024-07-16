package com.dish_dash.user.adapters.controller;

import com.dishDash.common.dto.CustomerDto;
import com.dishDash.common.dto.DeliveryPersonDto;
import com.dishDash.common.dto.RestaurantOwnerDto;
import com.dishDash.common.enums.DeliveryPersonStatus;
import com.dishDash.common.feign.user.UserApi;
import com.dish_dash.user.service.CustomerService;
import com.dish_dash.user.service.DeliveryPersonService;
import com.dish_dash.user.service.RestaurantOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController implements UserApi {

  private final CustomerService customerService;

  private final DeliveryPersonService deliveryPersonService;

  private final RestaurantOwnerService restaurantOwnerService;

  @Override
  public Boolean modifyCustomerProfile(Long id, CustomerDto customerDto) {
    return customerService.modifyProfile(id, customerDto);
  }

  @Override
  public CustomerDto getUserProfile(Long id) {
    return customerService.getUserProfile(id);
  }

  @Override
  public Boolean modifyRestaurantProfile(Long id, RestaurantOwnerDto restaurantOwnerDto) {
    return restaurantOwnerService.modifyProfile(id, restaurantOwnerDto);
  }

  @Override
  public Boolean modifyDeliveryPersonProfile(Long id, DeliveryPersonDto deliveryPersonDto) {
    return deliveryPersonService.modifyProfile(id, deliveryPersonDto);
  }

  @Override
  public void createCustomer(CustomerDto customerDto) {
    customerService.createCustomer(customerDto);
  }

  @Override
  public void createDeliveryPerson(DeliveryPersonDto deliveryPersonDto) {
    deliveryPersonService.createDeliveryPerson(deliveryPersonDto);
  }

  @Override
  public void createRestaurantOwner(RestaurantOwnerDto restaurantOwnerDto) {
    restaurantOwnerService.createRestaurantOwner(restaurantOwnerDto);
  }

  @Override
  public DeliveryPersonStatus getDeliveryPersonStatus(Long id) {
    return deliveryPersonService.getDeliveryPersonStatus(id);
  }
}
