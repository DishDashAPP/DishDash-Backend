package com.dish_dash.authentication.application.service;


import com.dish_dash.authentication.domain.model.AuthenticationInfo;
import com.dish_dash.authentication.domain.model.Token;
import com.dish_dash.authentication.infrastructure.repository.AuthenticationRepository;
import com.dish_dash.authentication.infrastructure.repository.TokenRepository;
import com.dish_dash.user.domain.model.Customer;
import com.dish_dash.user.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private TokenRepository tokenRepository;

    public Token login(String username, String password) {
        AuthenticationInfo authInfo = authenticationRepository.findByUsername(username);
        if (authInfo != null && authInfo.getPassword().equals(password)) {
            Token token = new Token();
            tokenRepository.create(token.getTokenID(), token.getValue(), token.getExpirationDate());
            return token;
        }
        return null;
    }

    public boolean logout(String tokenID) {
        Token token = tokenRepository.findByID(tokenID);
        if (token != null) {
            return token.invalidate();
        }
        return false;
    }

    public User register(String username, String password, String name) {
        if (authenticationRepository.register(username, password, name)) {
            return new Customer();
        }
        return null;
    }
}
