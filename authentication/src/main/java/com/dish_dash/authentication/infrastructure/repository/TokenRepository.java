package com.dish_dash.authentication.infrastructure.repository;

import com.dish_dash.authentication.domain.model.Token;

import java.util.Date;

public interface TokenRepository {
    Token findByID(String tokenID);
    boolean create(String tokenID, String value, Date expirationDate);
    boolean modify(String tokenID);
}
