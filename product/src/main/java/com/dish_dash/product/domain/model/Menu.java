package com.dish_dash.product.domain.model;

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
@Table(name = "menu")
public class Menu {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Category> categories;

  @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
  private List<Food> foodList;

  @Column(name = "restaurant_id")
  private long restaurantId;
}
