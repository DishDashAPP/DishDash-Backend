package com.dish_dash.order.domain.repository;

import com.dishDash.common.enums.OrderStatus;
import com.dish_dash.order.domain.model.Order;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

import com.dish_dash.order.domain.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
