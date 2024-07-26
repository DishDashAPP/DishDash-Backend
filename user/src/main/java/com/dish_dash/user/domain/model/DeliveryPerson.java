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

  @Id private long id;

  private String firstName;
  private String lastName;
  private String phoneNumber;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "location_id")
  private Location location;

  private long currentOrderId;

  @Column(name = "status", length = 32, columnDefinition = "varchar(32) default 'ACTIVE' ")
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private DeliveryPersonStatus status = DeliveryPersonStatus.ACTIVE;
}
