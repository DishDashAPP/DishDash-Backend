package com.dish_dash.user.service;

import com.dish_dash.user.domain.model.RestaurantOwner;
import com.dish_dash.user.adapters.repository.RestaurantOwnerRepository;
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
        RestaurantOwner savedRestaurantOwner = restaurantOwnerRepository.save(restaurantOwner);
        return savedRestaurantOwner != null;
    }

    public RestaurantOwner getUserProfile(String restaurantOwnerID) {
        return restaurantOwnerRepository.findById(restaurantOwnerID).orElse(null);
    }
}
