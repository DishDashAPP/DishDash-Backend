package com.dish_dash.delivery.infrastructure.repository;

import com.dish_dash.delivery.domain.model.Location;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository {
    Location findByID(String locationID);
    Location create(long latitude, long longitude, String deliveryID);
    boolean modify(Location location);
}
