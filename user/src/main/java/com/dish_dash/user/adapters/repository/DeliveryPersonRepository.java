package com.dish_dash.user.adapters.repository;

import com.dishDash.common.enums.DeliveryPersonStatus;
import com.dish_dash.user.domain.model.DeliveryPerson;
import com.dish_dash.user.domain.repository.UserRepository;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface DeliveryPersonRepository
    extends JpaRepository<DeliveryPerson, Long>, UserRepository<DeliveryPerson> {

  @Query(
      "UPDATE DeliveryPerson SET firstName =:firstName,lastName=:lastName,"
          + " phoneNumber =:phoneNumber where id=:id")
  @Modifying
  @Transactional
  void modify(
      @Param("firstName") String firstName,
      @Param("lastName") String lastName,
      @Param("phoneNumber") String phoneNumber,
      @Param("id") long id);

  Collection<DeliveryPerson> findByStatus(DeliveryPersonStatus deliveryPersonStatus);
}
