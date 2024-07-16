package com.dish_dash.user.domain.model;

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
@Table(name = "restaurant_owners")
public class RestaurantOwner implements User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String phoneNumber;
  private String address;
  private String username;

  @ElementCollection
  @CollectionTable(
      name = "restaurant_owner_menu_ids",
      joinColumns = @JoinColumn(name = "restaurant_owner_id"))
  @Column(name = "menu_id")
  private List<String> menuIds;

  @ElementCollection
  @CollectionTable(
      name = "restaurant_owner_active_order_ids",
      joinColumns = @JoinColumn(name = "restaurant_owner_id"))
  @Column(name = "active_order_id")
  private List<String> activeOrderIds;

  @ElementCollection
  @CollectionTable(
      name = "restaurant_owner_order_history_ids",
      joinColumns = @JoinColumn(name = "restaurant_owner_id"))
  @Column(name = "order_history_id")
  private List<String> orderHistoryIds;

  public boolean addActiveOrderID(String orderID) {
    return activeOrderIds.add(orderID);
  }
}
