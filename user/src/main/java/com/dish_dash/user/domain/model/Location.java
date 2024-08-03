package com.dish_dash.user.domain.model;

import java.sql.Timestamp;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

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

  private float latitude;
  private float longitude;
  @UpdateTimestamp private Timestamp timestamp;

  @Column(name = "delivery_id")
  private long deliveryID;
}
