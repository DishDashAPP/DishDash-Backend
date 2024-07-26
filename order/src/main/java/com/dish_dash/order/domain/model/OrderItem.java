package com.dish_dash.order.domain.model;

import com.dishDash.common.Price;
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
@Table(name = "order_item")
public class OrderItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "order_id", nullable = false)
  @ToString.Exclude
  private Order order;

  @Column(name = "food_id")
  private long foodId;

  @Embedded private Price price;

  private Integer quantity;
}
