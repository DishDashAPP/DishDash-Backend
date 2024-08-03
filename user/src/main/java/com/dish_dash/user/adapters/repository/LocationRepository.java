package com.dish_dash.user.adapters.repository;

import com.dish_dash.user.domain.model.Location;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

  Optional<Location> findByDeliveryID(Long deliveryID);

  @Query(
      "UPDATE Location SET latitude =:latitude, longitude =:longitude where id=:id")
  @Modifying
  @Transactional
  void modify(
      @Param("latitude") float latitude, @Param("longitude") float longitude, @Param("id") long id);
}
