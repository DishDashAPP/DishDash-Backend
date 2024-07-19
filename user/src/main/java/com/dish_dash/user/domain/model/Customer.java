package com.dish_dash.user.domain.model;

import java.util.ArrayList;
import java.util.List;
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
@Table(name = "customers")
public class Customer implements User {

  @Id
  private Long id;

  private String firstName;
  private String lastName;
  private String phoneNumber;
  private String address;

  @ElementCollection
  @CollectionTable(name = "customer_orders", joinColumns = @JoinColumn(name = "customer_id"))
  @Column(name = "order_id")
  @Singular
  private List<String> orderIds = new ArrayList<>();
}
