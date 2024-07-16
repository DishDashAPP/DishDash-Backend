package com.dish_dash.authentication.domain.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;
import java.util.UUID;


@Data
@Entity
public class Token {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @javax.persistence.Id
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
