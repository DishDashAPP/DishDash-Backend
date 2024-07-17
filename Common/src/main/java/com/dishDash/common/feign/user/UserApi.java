package com.dishDash.common.feign.user;

import static com.dishDash.common.util.UrlConstraint.*;

import com.dishDash.common.dto.CustomerDto;
import com.dishDash.common.dto.DeliveryPersonDto;
import com.dishDash.common.dto.LocationDto;
import com.dishDash.common.dto.RestaurantOwnerDto;
import com.dishDash.common.enums.DeliveryPersonStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service")
public interface UserApi {
  @PutMapping(CUSTOMER + "/{id}")
  Boolean modifyCustomerProfile(@PathVariable Long id, @RequestBody CustomerDto customerDto);

  @GetMapping(CUSTOMER + "/{id}")
  CustomerDto getUserProfile(@PathVariable Long id);

  @PutMapping(RESTAURANT_OWNER + "/{id}")
  Boolean modifyRestaurantProfile(
      @PathVariable Long id, @RequestBody RestaurantOwnerDto restaurantOwnerDto);

  @PutMapping(DELIVERY_PERSON + "/{id}")
  Boolean modifyDeliveryPersonProfile(
      @PathVariable Long id, @RequestBody DeliveryPersonDto deliveryPersonDto);

  @PostMapping(CUSTOMER)
  void createCustomer(@RequestBody CustomerDto customerDto);

  @PostMapping(DELIVERY_PERSON)
  void createDeliveryPerson(@RequestBody DeliveryPersonDto deliveryPersonDto);

  @PostMapping(RESTAURANT_OWNER)
  void createRestaurantOwner(@RequestBody RestaurantOwnerDto restaurantOwnerDto);

  @GetMapping(DELIVERY_PERSON + "/status/{id}")
  DeliveryPersonStatus getDeliveryPersonStatus(@PathVariable Long id);

  @PostMapping("/setLocation")
  public boolean setLocation(@RequestBody LocationDto locationDto, @RequestParam long deliveryPersonId);

  @GetMapping("/getLocation")
  public LocationDto getLocation(@RequestParam long deliveryPersonId);
}
