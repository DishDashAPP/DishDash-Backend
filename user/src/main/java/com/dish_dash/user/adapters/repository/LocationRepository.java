package com.dish_dash.user.adapters.repository;

import com.dish_dash.user.domain.model.Location;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository {
    Location findByID(String locationID);
    Location create(long latitude, long longitude, String deliveryID);
    boolean modify(Location location);
}
