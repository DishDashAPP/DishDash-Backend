package com.dish_dash.authentication.infrastructure.repository;

import com.dish_dash.authentication.domain.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByTokenID(String tokenID);

    @Override
    <S extends Token> S save(S entity);

    Optional<Token> findByValue(String value);

    void deleteByTokenID(String tokenID);
}
