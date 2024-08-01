package com.dish_dash.user.service;

import com.dishDash.common.dto.RestaurantOwnerDto;
import com.dish_dash.user.adapters.repository.RestaurantOwnerRepository;
import com.dish_dash.user.domain.mapper.UserMapper;
import com.dish_dash.user.domain.model.RestaurantOwner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantOwnerService {

  private final RestaurantOwnerRepository restaurantOwnerRepository;

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
        .orElse(false);
  }

  public void createRestaurantOwner(RestaurantOwnerDto restaurantOwnerDto) {
    restaurantOwnerRepository.save(
        RestaurantOwner.builder().id(restaurantOwnerDto.getId()).build());
  }

  public RestaurantOwnerDto getUserProfile(long restaurantOwnerId) {
    return restaurantOwnerRepository
        .findById(restaurantOwnerId)
        .map(UserMapper.INSTANCE::restaurantOwnerToDto)
        .orElse(null);
  }

  public List<RestaurantOwnerDto> getAllRestaurant() {
    return restaurantOwnerRepository.findAll().stream()
        .map(UserMapper.INSTANCE::restaurantOwnerToDto)
        .toList();
  }
}
