package com.dish_dash.authentication.domain.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
public class Token {
    private String tokenID;
    private String value;
    private Date expirationDate;

    public Token() {
        this.tokenID = UUID.randomUUID().toString();
        this.value = generateTokenValue();
        this.expirationDate = generateExpirationDate();
    }

    public boolean validate() {
        return new Date().before(this.expirationDate);
    }

    public boolean invalidate() {
        this.expirationDate = new Date();
        return true;
    }

    private String generateTokenValue() {
        return UUID.randomUUID().toString();
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
    }
}
