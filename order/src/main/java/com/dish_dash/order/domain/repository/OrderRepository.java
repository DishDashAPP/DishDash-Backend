package com.dish_dash.order.domain.repository;

import com.dishDash.common.enums.OrderStatus;
import com.dish_dash.order.domain.model.Order;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  List<Order> findAllByRestaurantOwnerIdAndStatusInOrderByIdDesc(
      long restaurantOwnerId, List<OrderStatus> status);

  List<Order> findAllByRestaurantOwnerId(long restaurantOwnerId);

  Optional<Order> findByDeliveryPersonId(long deliveryPersonId);

  List<Order> findByCustomerIdOrderByIdDesc(long customerID);

  @Query("UPDATE Order SET status =:status where id=:id")
  @Modifying
  @Transactional
  void updateStatus(@Param("status") OrderStatus status, @Param("id") long id);

  @Query("Update Order set deliveryPersonId =:deliveryPersonId where id =:id")
  @Modifying
  @Transactional
  void updateDeliveryPerson(@Param("deliveryPersonId") long deliveryPersonId, @Param("id") long id);

  Optional<Order> findByCustomerIdAndStatusNot(long customerId, OrderStatus status);

  Optional<Order> findByCustomerIdAndStatus(long customerId, OrderStatus status);

  Optional<Order> findByDeliveryPersonIdAndStatus(long deliveryPersonID, OrderStatus orderStatus);
}
