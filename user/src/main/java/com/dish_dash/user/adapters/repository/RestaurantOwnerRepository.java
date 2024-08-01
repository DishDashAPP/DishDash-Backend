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
          "UPDATE RestaurantOwner SET firstName =:firstName,lastName=:lastName, address =:address,restaurantName=:restaurantName, phoneNumber =:phoneNumber where id=:id")
  @Modifying
  @Transactional
  void modify(
          @Param("firstName") String firstName,
          @Param("lastName") String lastName,
          @Param("address") String address,
          @Param("restaurantName") String restaurantName,
          @Param("phoneNumber") String phoneNumber,
          @Param("id") long id);
}
