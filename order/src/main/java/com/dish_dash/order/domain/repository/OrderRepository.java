package com.dish_dash.order.domain.repository;

import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.model.OrderStatus;
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

  List<Order> findAllByRestaurantOwnerIdAndStatusIn(
      Long restaurantOwnerId, List<OrderStatus> status);

  //  Order findCurrentOrderByDeliveryPersonID(String deliveryPersonId);

  List<Order> findByCustomerId(Long customerID);

  @Query("UPDATE Order SET status =:status where id=:id")
  @Modifying
  @Transactional
  void updateStatus(@Param("status") OrderStatus status, @Param("id") Long id);

  Optional<Order> findCurrentOrderByCustomerId(Long customerId);
}
