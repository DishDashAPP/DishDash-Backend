package com.dish_dash.product.domain.model;

import com.dishDash.common.Price;
import com.dishDash.common.enums.CurrencyUnit;
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
@Table(name = "food")
public class Food {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;
  private String description;

  @Embedded @Builder.Default
  private Price price = Price.builder().amount(0.0).unit(CurrencyUnit.TOMAN).build();

  private Integer stock;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @ManyToOne
  @JoinColumn(name = "menu_id")
  private Menu menu;
}
