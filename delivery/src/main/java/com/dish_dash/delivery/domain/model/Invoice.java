package com.dish_dash.delivery.domain.model;

import java.sql.Time;
import javax.persistence.*;
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
@Table(name = "invoice")
public class Invoice {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String invoiceID;

  @Column(name = "customer_id")
  private Long customerId;

  @Column(name = "order_id")
  private Long orderId;

  @Column(name = "delivery_person_id")
  private Long deliveryPersonId;

  @Column(name = "create_time")
  @CreationTimestamp
  private Time creationTime;
}
