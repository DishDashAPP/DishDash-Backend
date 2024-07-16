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
  Optional<RestaurantOwner> findByUsername(String username);
  @Query(
          "UPDATE RestaurantOwner SET name =:name, address =:address, phoneNumber =:phoneNumber, username =:username where id=:id")
  @Modifying
  @Transactional
  void modify(
          @Param("name") String name,
          @Param("address") String address,
          @Param("phoneNumber") String phoneNumber,
          @Param("username") String username,
          @Param("id") Long id);
}
