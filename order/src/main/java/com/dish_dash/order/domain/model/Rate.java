package com.dish_dash.order.domain.model;

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
@Table(name = "rete")
public class Rate {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  private Integer point;

  @Column(name = "customer_id")
  private Long customerId;

  @Column(name = "order_id")
  private Long orderId;
}
