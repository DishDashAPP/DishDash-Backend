package com.dish_dash.user.domain.model;

import com.dishDash.common.enums.DeliveryPersonStatus;
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
@Table(name = "delivery_persons")
public class DeliveryPerson implements User {

  @Id private Long id;

  private String firstName;
  private String lastName;
  private String phoneNumber;

  @ManyToOne
  @JoinColumn(name = "location_id")
  private Location location;

  private Long currentOrderId;
  private DeliveryPersonStatus status;
}
