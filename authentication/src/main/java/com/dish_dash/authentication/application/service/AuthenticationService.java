package com.dish_dash.authentication.application.service;

import com.dish_dash.authentication.domain.model.AuthenticationInfo;
import com.dish_dash.authentication.domain.model.Token;
import com.dish_dash.authentication.infrastructure.repository.AuthenticationRepository;
import com.dish_dash.authentication.infrastructure.repository.TokenRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

  private final AuthenticationRepository authRepository;

  private final TokenRepository tokenRepository;

  private final BCryptPasswordEncoder passwordEncoder;

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

  public String validateToken(String token) {
    return tokenRepository
        .findByValue(token)
        .flatMap(
            t -> {
              log.info("Token found: {}", t);
              return authRepository.findById(t.getValue());
            })
        .map(
            authenticationInfo -> {
              log.info("Authentication info found: {}", authenticationInfo);
              return authenticationInfo.getUsername();
            })
        .orElseGet(
            () -> {
              log.warn("Token or authentication info not found for token: {}", token);
              return null;
            });
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
