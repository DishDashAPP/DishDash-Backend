package com.dish_dash.authentication.application.service;

import com.dish_dash.authentication.domain.model.AuthenticationInfo;
import com.dish_dash.authentication.domain.model.Token;
import com.dish_dash.authentication.infrastructure.repository.AuthenticationRepository;
import com.dish_dash.authentication.infrastructure.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationService {

  @Autowired private AuthenticationRepository authRepository;

  @Autowired private TokenRepository tokenRepository;

  @Autowired private BCryptPasswordEncoder passwordEncoder;

  public String login(String username, String password) {
    AuthenticationInfo authInfo = authRepository.findByUsername(username);
    if (authInfo != null && passwordEncoder.matches(password, authInfo.getPassword())) {
      Token token = new Token();
      token.setValue(UUID.randomUUID().toString());
      token.setTokenID(authInfo.getUserID());
      tokenRepository.save(token);
      return token.getValue();
    }
    return null;
  }

  public boolean validateToken(String token) {
    Optional<Token> foundToken =
        tokenRepository.findByValue(token); // Changed findByToken to findByValue
    return foundToken.isPresent();
  }

  public void logout(String token) {
    Optional<Token> foundToken =
        tokenRepository.findByValue(token); // Changed findByToken to findByValue
    foundToken.ifPresent(value -> tokenRepository.delete(value));
  }

  public void register(String username, String password, String roles) {
    AuthenticationInfo authInfo = new AuthenticationInfo();
    authInfo.setUsername(username);
    authInfo.setPassword(passwordEncoder.encode(password));
    authInfo.setRoles(roles);
    authRepository.save(authInfo);
  }
}
