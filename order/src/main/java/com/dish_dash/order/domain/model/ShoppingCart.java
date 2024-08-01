package com.dish_dash.order.domain.model;

import com.dishDash.common.Price;
import com.dishDash.common.enums.CurrencyUnit;

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
@Table(name = "shopping_cart")
public class ShoppingCart {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "shoppingCart")
  private List<ShoppingCartItem> shoppingCartItems;

  @Column(name = "restaurant_owner_id")
  private long restaurantOwnerId;

  @Column(name = "customer_id")
  private long customerId;

  @Column(name = "total_price")
  @Embedded
  private Price totalPrice;

  @PostLoad
  private void calculateTotalPrice() {
    double total = shoppingCartItems.stream().mapToDouble(item -> item.getPrice().getAmount()).sum();
    this.totalPrice = new Price(total, CurrencyUnit.TOMAN);
  }
}
