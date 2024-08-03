package com.dish_dash.product.infrastructure.repository;

import com.dish_dash.product.domain.model.Menu;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
  Optional<Menu> findByRestaurantId(long aLong);

  @Modifying
  @Transactional
  @Query("DELETE FROM Menu m WHERE m.restaurantId = :ownerId")
  void deleteByRestaurantOwnerId(long ownerId);

  boolean existsByRestaurantId(long id);
}
