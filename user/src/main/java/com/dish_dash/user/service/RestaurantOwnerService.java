package com.dish_dash.user.service;

import com.dish_dash.user.domain.RestaurantOwner;
import com.dish_dash.user.repository.RestaurantOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantOwnerService {

    @Autowired
    private RestaurantOwnerRepository restaurantOwnerRepository;

    public RestaurantOwner modifyProfile(RestaurantOwner restaurantOwner) {
        return restaurantOwnerRepository.save(restaurantOwner);
    }

    public boolean createRestaurantOwner(RestaurantOwner restaurantOwner) {
        RestaurantOwner savedOwner = restaurantOwnerRepository.save(restaurantOwner);
        return savedOwner != null;
    }

    public RestaurantOwner getUserProfile(String restaurantOwnerID) {
        return restaurantOwnerRepository.findById(restaurantOwnerID).orElse(null);
    }
}
