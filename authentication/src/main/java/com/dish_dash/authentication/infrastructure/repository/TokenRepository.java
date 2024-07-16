package com.dish_dash.authentication.infrastructure.repository;

import com.dish_dash.authentication.domain.model.Token;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TokenRepository {
    Token findByID(String tokenID);
    boolean create(String tokenID, String value, Date expirationDate);
    boolean modify(String tokenID);

    Token save(Token token);

    Token findByToken(String token);

    void delete(Token foundToken);
}
