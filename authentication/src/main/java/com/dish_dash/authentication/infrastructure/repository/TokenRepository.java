package com.dish_dash.authentication.infrastructure.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.dish_dash.authentication.domain.model.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, String> {
}
