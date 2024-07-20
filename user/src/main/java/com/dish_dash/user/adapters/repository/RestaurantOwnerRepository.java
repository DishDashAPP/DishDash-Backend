package com.dish_dash.user.adapters.repository;

import com.dish_dash.user.domain.model.RestaurantOwner;
import com.dish_dash.user.domain.repository.UserRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface RestaurantOwnerRepository
    extends JpaRepository<RestaurantOwner, Long>, UserRepository<RestaurantOwner> {
  @Query(
          "UPDATE RestaurantOwner SET firstName =:firstName, address =:address, phoneNumber =:phoneNumber where id=:id")
  @Modifying
  @Transactional
  void modify(
          @Param("firstName") String firstName,
          @Param("address") String address,
          @Param("phoneNumber") String phoneNumber,
          @Param("id") Long id);
}
