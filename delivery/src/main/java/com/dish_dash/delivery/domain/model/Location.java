package com.dish_dash.delivery.domain.model;

import java.time.LocalDateTime;

public class Location {
    private String locationID;
    private long latitude;
    private long longitude;
    private LocalDateTime timestamp;
    private String deliveryID;

    public Location(long latitude, long longitude, String deliveryID) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = LocalDateTime.now();
        this.deliveryID = deliveryID;
        this.locationID = generateLocationID();
    }

    private String generateLocationID() {
        // Logic to generate location ID
        return "LOC-" + System.currentTimeMillis();
    }

    public String getLocationID() {
        return locationID;
    }

    public long getLatitude() {
        return latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getDeliveryID() {
        return deliveryID;
    }
}
