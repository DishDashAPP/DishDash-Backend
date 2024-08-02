package com.dish_dash.order.domain.model;

import com.dishDash.common.Price;
import lombok.*;

import javax.persistence.*;

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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "shopping_cart_id")
  private ShoppingCart shoppingCart;

  @Column(name = "food_id")
  private long foodId;

  @Embedded private Price price;

  private Integer quantity;
}
