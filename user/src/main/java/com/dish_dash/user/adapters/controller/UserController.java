package com.dish_dash.user.adapters.controller;

import com.dishDash.common.dto.CustomerDto;
import com.dishDash.common.dto.DeliveryPersonDto;
import com.dishDash.common.dto.LocationDto;
import com.dishDash.common.dto.RestaurantOwnerDto;
import com.dishDash.common.enums.DeliveryPersonStatus;
import com.dishDash.common.feign.user.UserApi;
import com.dish_dash.user.service.CustomerService;
import com.dish_dash.user.service.DeliveryPersonService;
import com.dish_dash.user.service.RestaurantOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

  private final CustomerService customerService;

  private final DeliveryPersonService deliveryPersonService;

  private final RestaurantOwnerService restaurantOwnerService;

  @Override
  public Boolean modifyCustomerProfile(long id, CustomerDto customerDto) {
    return customerService.modifyProfile(id, customerDto);
  }

  @Override
  public CustomerDto getCustomerProfile(long id) {
    return customerService.getUserProfile(id);
  }

  @Override
  public RestaurantOwnerDto getRestaurantOwnerProfile(long id) {
    return restaurantOwnerService.getUserProfile(id);
  }

  @Override
  public DeliveryPersonDto getDeliveryPersonProfile(long id) {
    return deliveryPersonService.getUserProfile(id);
  }

  @Override
  public Boolean modifyRestaurantProfile(long id, RestaurantOwnerDto restaurantOwnerDto) {
    return restaurantOwnerService.modifyProfile(id, restaurantOwnerDto);
  }

  @Override
  public Boolean modifyDeliveryPersonProfile(long id, DeliveryPersonDto deliveryPersonDto) {
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
  public DeliveryPersonStatus getDeliveryPersonStatus(long id) {
    return deliveryPersonService.getDeliveryPersonStatus(id);
  }

  @Override
  public boolean setLocation(
      @RequestBody LocationDto locationDto, @RequestParam long deliveryPersonId) {
    return deliveryPersonService.setLocation(locationDto, deliveryPersonId);
  }

  @Override
  public LocationDto getLocation(@RequestParam long deliveryPersonId) {
    return deliveryPersonService.getLocation(deliveryPersonId);
  }

    @Override
    public Long setActiveOrder(@RequestParam long orderId) {
        return deliveryPersonService.setActiveOrder(orderId);
    }
}
