package com.dish_dash.user.domain.mapper;

import com.dishDash.common.dto.CustomerDto;
import com.dishDash.common.dto.DeliveryPersonDto;
import com.dishDash.common.dto.LocationDto;
import com.dishDash.common.dto.RestaurantOwnerDto;
import com.dish_dash.user.domain.model.Customer;
import com.dish_dash.user.domain.model.DeliveryPerson;
import com.dish_dash.user.domain.model.Location;
import com.dish_dash.user.domain.model.RestaurantOwner;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  CustomerDto customerToDto(Customer customer);

  Customer dtoToCustomer(CustomerDto customerDto);

  DeliveryPersonDto deliveryPersonToDto(DeliveryPerson deliveryPerson);

  DeliveryPerson dtoToDeliveryPerson(DeliveryPersonDto deliveryPersonDto);

  RestaurantOwnerDto restaurantOwnerToDto(RestaurantOwner restaurantOwner);

  RestaurantOwner dtoToRestaurantOwner(RestaurantOwnerDto restaurantOwnerDto);

  LocationDto locationToDto(Location location);

  void updateCustomerFromDto(CustomerDto customerDto, @MappingTarget Customer customer);

  void updateRestaurantOwnerFromDto(
      RestaurantOwnerDto restaurantOwnerDto, @MappingTarget RestaurantOwner restaurantOwner);

  void updateDeliveryPersonFromDto(
      DeliveryPersonDto deliveryPersonDto, @MappingTarget DeliveryPerson deliveryPerson);

  void updateLocationFromDto(LocationDto locationDto, @MappingTarget Location location);
}
