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
    // Check if the restaurant owner exists, if not, create a new one
    return restaurantOwnerRepository
            .findById(id)
            .map(
                    restaurantOwner -> {
                      // Modify existing restaurant owner details
                      restaurantOwnerRepository.modify(
                              restaurantOwnerDto.getFirstName(),
                              restaurantOwnerDto.getLastName(),
                              restaurantOwnerDto.getAddress(),
                              restaurantOwnerDto.getRestaurantName(),
                              restaurantOwnerDto.getPhoneNumber(),
                              id);
                      return true;
                    })
            .orElseGet(() -> {
              // Insert new restaurant owner if not found
              RestaurantOwner newRestaurantOwner = UserMapper.INSTANCE.dtoToRestaurantOwner(restaurantOwnerDto);
              newRestaurantOwner.setId(id); // Ensure the ID from the argument is set
              restaurantOwnerRepository.save(newRestaurantOwner);
              return true;
            });
  }

  public void createRestaurantOwner(RestaurantOwnerDto restaurantOwnerDto) {
    // Create a new restaurant owner from RestaurantOwnerDto
    RestaurantOwner newRestaurantOwner = UserMapper.INSTANCE.dtoToRestaurantOwner(restaurantOwnerDto);
    restaurantOwnerRepository.save(newRestaurantOwner);
  }

  public RestaurantOwnerDto getUserProfile(long restaurantOwnerId) {
    // Get restaurant owner profile
    return restaurantOwnerRepository
            .findById(restaurantOwnerId)
            .map(UserMapper.INSTANCE::restaurantOwnerToDto)
            .orElse(null);
  }

  public List<RestaurantOwnerDto> getAllRestaurant() {
    // Get all restaurant owners
    return restaurantOwnerRepository.findAll().stream()
            .map(UserMapper.INSTANCE::restaurantOwnerToDto)
            .collect(Collectors.toList());
  }
}
