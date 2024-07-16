package com.dish_dash.user.service;

import com.dishDash.common.dto.RestaurantOwnerDto;
import com.dish_dash.user.adapters.repository.RestaurantOwnerRepository;
import com.dish_dash.user.domain.mapper.UserMapper;
import com.dish_dash.user.domain.model.RestaurantOwner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantOwnerService {

  private final RestaurantOwnerRepository restaurantOwnerRepository;

  public Boolean modifyProfile(Long id, RestaurantOwnerDto restaurantOwnerDto) {
    return restaurantOwnerRepository
        .findById(id)
        .map(
            restaurantOwner -> {
              UserMapper.INSTANCE.updateRestaurantOwnerFromDto(restaurantOwnerDto, restaurantOwner);
              restaurantOwnerRepository.save(restaurantOwner);
              return true;
            })
        .orElse(false);
  }

  public void createRestaurantOwner(RestaurantOwnerDto restaurantOwnerDto) {
    restaurantOwnerRepository.save(UserMapper.INSTANCE.dtoToRestaurantOwner(restaurantOwnerDto));
  }

  public RestaurantOwner getUserProfile(Long restaurantOwnerId) {
    return restaurantOwnerRepository.findById(restaurantOwnerId).orElse(null);
  }
}
