package com.dish_dash.user.service;

import com.dishDash.common.dto.RestaurantOwnerDto;
import com.dishDash.common.feign.order.RateApi;
import com.dish_dash.user.adapters.repository.RestaurantOwnerRepository;
import com.dish_dash.user.domain.mapper.UserMapper;
import com.dish_dash.user.domain.model.RestaurantOwner;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantOwnerService {

  private final RestaurantOwnerRepository restaurantOwnerRepository;
  private final RateApi rateApi;

  public Boolean modifyProfile(long id, RestaurantOwnerDto restaurantOwnerDto) {
    return restaurantOwnerRepository
        .findById(id)
        .map(
            restaurantOwner -> {
              restaurantOwnerRepository.modify(
                  restaurantOwnerDto.getFirstName(),
                  restaurantOwnerDto.getLastName(),
                  restaurantOwnerDto.getAddress(),
                  restaurantOwnerDto.getRestaurantName(),
                  restaurantOwnerDto.getPhoneNumber(),
                  id);
              return true;
            })
        .orElseGet(
            () -> {
              RestaurantOwner newRestaurantOwner =
                  UserMapper.INSTANCE.dtoToRestaurantOwner(restaurantOwnerDto);
              newRestaurantOwner.setId(id);
              restaurantOwnerRepository.save(newRestaurantOwner);
              return true;
            });
  }

  public void createRestaurantOwner(RestaurantOwnerDto restaurantOwnerDto) {
    RestaurantOwner newRestaurantOwner =
        UserMapper.INSTANCE.dtoToRestaurantOwner(restaurantOwnerDto);
    restaurantOwnerRepository.save(newRestaurantOwner);
  }

  public RestaurantOwnerDto getUserProfile(long restaurantOwnerId) {
    return restaurantOwnerRepository
        .findById(restaurantOwnerId)
        .map(UserMapper.INSTANCE::restaurantOwnerToDto)
        .orElse(null);
  }

  public List<RestaurantOwnerDto> getAllRestaurant() {
    List<RestaurantOwnerDto> response = new ArrayList<>();
    List<RestaurantOwner> restaurantOwners = restaurantOwnerRepository.findAll();
    for (RestaurantOwner restaurantOwner : restaurantOwners) {
      RestaurantOwnerDto restaurantOwnerDto =
          UserMapper.INSTANCE.restaurantOwnerToDto(restaurantOwner);
      restaurantOwnerDto.setRestaurantComments(
          rateApi.getRestaurantComments(restaurantOwner.getId()));
      response.add(restaurantOwnerDto);
    }
    return response;
  }
}
