package com.dish_dash.user.adapters.repository;

import com.dish_dash.user.domain.model.DeliveryPerson;
import com.dish_dash.user.domain.repository.UserRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryPersonRepository
    extends JpaRepository<DeliveryPerson, Long>, UserRepository<DeliveryPerson> {

  @Query(
      "UPDATE DeliveryPerson SET firstName =:firstName, phoneNumber =:phoneNumber where id=:id")
  @Modifying
  @Transactional
  void modify(
      @Param("firstName") String firstName,
      @Param("phoneNumber") String phoneNumber,
      @Param("username") String username,
      @Param("id") Long id);
}
