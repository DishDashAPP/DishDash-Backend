package com.dish_dash.order.infrastructure.repository;

import com.dish_dash.order.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataOrderRepository extends JpaRepository<Order, String> {}
