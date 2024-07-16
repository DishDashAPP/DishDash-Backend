package com.dish_dash.user.domain.model;

import javax.persistence.*;

import com.dishDash.common.enums.DeliveryPersonStatus;
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

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String phoneNumber;

  @ManyToOne
  @JoinColumn(name = "location_id")
  private Location location;

  private Long currentOrderId;
  private DeliveryPersonStatus status;
  private String username;
}
