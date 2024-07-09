package com.dish_dash.user.adapters.repository;

import com.dish_dash.user.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dish_dash.user.domain.model.DeliveryPerson;

@Repository
public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, String>, UserRepository<DeliveryPerson> {
    DeliveryPerson findByUsername(String username);
}
