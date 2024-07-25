package com.dish_dash.user.domain.model;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "locations")
public class Location {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private long latitude;
  private long longitude;
  private LocalDateTime timestamp;

  @Column(name = "delivery_id")
  private long deliveryID;
}
