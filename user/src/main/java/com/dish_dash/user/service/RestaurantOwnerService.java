package com.dish_dash.user.service;

import com.dishDash.common.dto.RestaurantOwnerDto;
import com.dishDash.common.enums.ErrorCode;
import com.dishDash.common.exception.CustomException;
import com.dishDash.common.feign.authentication.AuthenticationApi;
import com.dishDash.common.feign.order.RateApi;
import com.dish_dash.user.adapters.repository.RestaurantOwnerRepository;
import com.dish_dash.user.domain.mapper.UserMapper;
import com.dish_dash.user.domain.model.RestaurantOwner;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantOwnerService {

  private final RestaurantOwnerRepository restaurantOwnerRepository;
  private final RateApi rateApi;
  private final AuthenticationApi authenticationApi;

  public boolean modifyProfile(long id, RestaurantOwnerDto restaurantOwnerDto) {
    boolean exists = restaurantOwnerRepository.existsById(id);
    if (exists) {
      log.info("Modifying profile for restaurant owner ID: {}", id);
      restaurantOwnerRepository.modify(
          restaurantOwnerDto.getFirstName(),
          restaurantOwnerDto.getLastName(),
          restaurantOwnerDto.getAddress(),
          restaurantOwnerDto.getRestaurantName(),
          restaurantOwnerDto.getPhoneNumber(),
          id);
    } else {
      log.info("Restaurant owner ID: {} not found, creating new profile", id);
      RestaurantOwner newRestaurantOwner =
          UserMapper.INSTANCE.dtoToRestaurantOwner(restaurantOwnerDto);
      newRestaurantOwner.setId(id);
      restaurantOwnerRepository.save(newRestaurantOwner);
    }
    return true;
  }

  public void createRestaurantOwner(RestaurantOwnerDto restaurantOwnerDto) {
    log.info("Creating new restaurant owner with ID: {}", restaurantOwnerDto.getId());
    RestaurantOwner newRestaurantOwner =
        UserMapper.INSTANCE.dtoToRestaurantOwner(restaurantOwnerDto);
    restaurantOwnerRepository.save(newRestaurantOwner);
    log.info("Successfully created restaurant owner with ID: {}", newRestaurantOwner.getId());
  }

  public RestaurantOwnerDto getUserProfile(long restaurantOwnerId) {
    log.info("Retrieving user profile for restaurant owner ID: {}", restaurantOwnerId);
    String username = authenticationApi.getUsername(restaurantOwnerId);
    return restaurantOwnerRepository
        .findById(restaurantOwnerId)
        .map(
            restaurantOwner -> {
              RestaurantOwnerDto restaurantOwnerDto =
                  UserMapper.INSTANCE.restaurantOwnerToDto(restaurantOwner);
              restaurantOwnerDto.setUsername(username);
              restaurantOwnerDto.setRestaurantComments(
                  rateApi.getRestaurantComments(restaurantOwnerId));
              return restaurantOwnerDto;
            })
        .orElseThrow(
            () -> {
              log.error("getUserProfile. Restaurant owner ID: {} not found", restaurantOwnerId);
              return new CustomException(ErrorCode.NOT_FOUND, "Restaurant owner not found");
            });
  }

  public List<RestaurantOwnerDto> getAllRestaurant() {
    log.info("Retrieving all restaurant owners");
    return restaurantOwnerRepository.findAll().stream()
        .map(
            restaurantOwner -> {
              String username = authenticationApi.getUsername(restaurantOwner.getId());
              RestaurantOwnerDto restaurantOwnerDto =
                  UserMapper.INSTANCE.restaurantOwnerToDto(restaurantOwner);
              restaurantOwnerDto.setRestaurantComments(
                  rateApi.getRestaurantComments(restaurantOwner.getId()));
              restaurantOwnerDto.setUsername(username);
              return restaurantOwnerDto;
            })
        .collect(Collectors.toList());
  }
}
