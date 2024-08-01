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
@Table(name = "shopping_cart_item")
public class ShoppingCartItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "shopping_cart_id", nullable = false)
  @ToString.Exclude
  private ShoppingCart shoppingCart;

  @Column(name = "food_id")
  private long foodId;

  @Embedded private Price price;

  private Integer quantity;
}
