package com.dish_dash.user.adapters.repository;

import com.dish_dash.user.domain.model.Customer;
import com.dish_dash.user.domain.repository.UserRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository
    extends JpaRepository<Customer, Long>, UserRepository<Customer> {

  Optional<Customer> findByPhoneNumber(String phoneNumber);

  @Query(
      "UPDATE Customer SET firstName =:firstName,lastName=:lastName," +
              " address =:address, phoneNumber =:phoneNumber where id=:id")
  @Modifying
  @Transactional
  void modify(
      @Param("firstName") String firstName,
      @Param("lastName") String lastName,
      @Param("address") String address,
      @Param("phoneNumber") String phoneNumber,
      @Param("id") long id);
}
