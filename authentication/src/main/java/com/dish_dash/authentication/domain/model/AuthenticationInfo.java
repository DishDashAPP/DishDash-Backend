package com.dish_dash.authentication.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
public class AuthenticationInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userID;
    private String username;
    private String password;
    private String roles;
}
