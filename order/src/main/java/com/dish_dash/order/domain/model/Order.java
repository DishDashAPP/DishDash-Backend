package com.dish_dash.order.domain.model;

import com.dishDash.common.Price;
import com.dishDash.common.enums.CurrencyUnit;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.*;

import com.dishDash.common.enums.OrderStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "create_time")
  @CreationTimestamp
  private Timestamp createTime;

  @Column(name = "status", length = 32, columnDefinition = "varchar(32) default 'PREPARING' ")
  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order")
  private List<OrderItem> orderItems;

  @Column(name = "restaurant_owner_id")
  private long restaurantOwnerId;

  @Column(name = "delivery_person_id", columnDefinition = "bigint default -1")
  private long deliveryPersonId;

  @Column(name = "customer_id")
  private long customerId;

  @OneToOne(cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private Review review;

  @OneToOne(cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private Rate restaurantRate;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @PrimaryKeyJoinColumn
  private Rate deliveryPersonRate;

  @Column(name = "total_price")
  @Embedded
  private Price totalPrice;

  @PostLoad
  private void calculateTotalPrice() {
    double total = orderItems.stream().mapToDouble(item -> item.getPrice().getAmount()).sum();
    this.totalPrice = new Price(total, CurrencyUnit.TOMAN);
  }
}
