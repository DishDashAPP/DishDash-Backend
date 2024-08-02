package com.dish_dash.user.service;

import com.dishDash.common.dto.RestaurantOwnerDto;
import com.dishDash.common.feign.authentication.AuthenticationApi;
import com.dishDash.common.feign.order.RateApi;
import com.dish_dash.user.adapters.repository.RestaurantOwnerRepository;
import com.dish_dash.user.domain.mapper.UserMapper;
import com.dish_dash.user.domain.model.RestaurantOwner;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantOwnerService {

  private final RestaurantOwnerRepository restaurantOwnerRepository;
  private final RateApi rateApi;
  private final AuthenticationApi authenticationApi;

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
    String username = authenticationApi.getUsername(restaurantOwnerId);
    Optional<RestaurantOwner> restaurantOwnerOptional =
        restaurantOwnerRepository.findById(restaurantOwnerId);
    if (restaurantOwnerOptional.isPresent()) {
      RestaurantOwnerDto restaurantOwnerDto =
          UserMapper.INSTANCE.restaurantOwnerToDto(restaurantOwnerOptional.get());
      restaurantOwnerDto.setUsername(username);
      restaurantOwnerDto.setRestaurantComments(rateApi.getRestaurantComments(restaurantOwnerId));
      return restaurantOwnerDto;
    }
    return null;
  }

  public List<RestaurantOwnerDto> getAllRestaurant() {
    List<RestaurantOwnerDto> response = new ArrayList<>();
    List<RestaurantOwner> restaurantOwners = restaurantOwnerRepository.findAll();
    for (RestaurantOwner restaurantOwner : restaurantOwners) {
      String username = authenticationApi.getUsername(restaurantOwner.getId());
      RestaurantOwnerDto restaurantOwnerDto =
          UserMapper.INSTANCE.restaurantOwnerToDto(restaurantOwner);
      restaurantOwnerDto.setRestaurantComments(
          rateApi.getRestaurantComments(restaurantOwner.getId()));
      restaurantOwnerDto.setUsername(username);
      response.add(restaurantOwnerDto);
    }
    return response;
  }
}
