package com.dish_dash.user.domain.model;

import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "locations")
@NoArgsConstructor
@Embeddable
public class Location {
    @Id
    private String id;
    private long latitude;
    private long longitude;
    private LocalDateTime timestamp;
    private String deliveryID;

    public Location(long latitude, long longitude, String deliveryID) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = LocalDateTime.now();
        this.deliveryID = deliveryID;
        this.id = generateLocationID();
    }

    private String generateLocationID() {
        return "LOC-" + System.currentTimeMillis();
    }
}
