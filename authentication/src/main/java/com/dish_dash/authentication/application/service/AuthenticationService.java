package com.dish_dash.authentication.application.service;


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
            return new User(username, name);
        }
        return null;
    }
}
