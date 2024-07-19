package com.dish_dash.authentication.application.service;

import com.dishDash.common.dto.AuthDto;
import com.dishDash.common.dto.CustomerDto;
import com.dishDash.common.dto.DeliveryPersonDto;
import com.dishDash.common.enums.ErrorCode;
import com.dishDash.common.enums.Role;
import com.dishDash.common.exception.CustomException;
import com.dishDash.common.feign.user.UserApi;
import com.dish_dash.authentication.domain.model.AuthenticationInfo;
import com.dish_dash.authentication.infrastructure.repository.AuthenticationRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
  private final AuthenticationRepository authRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final RedisTemplate<String, Long> redisTemplate;
  private final UserApi userApi;

  private static final String SECRET_KEY = "your-256-bit-secret";

  public String login(String username, String password) {
    AuthenticationInfo authInfo = authRepository.findByUsername(username);
    if (authInfo != null && passwordEncoder.matches(password, authInfo.getPassword())) {
      return generateToken(authInfo.getUserId());
    }
    return null;
  }

  public AuthDto validateToken(String tokenValue) {
    Claims claims = extractAllClaims(tokenValue);
    claims.getSubject();
    String userId = claims.getSubject();
    if (Boolean.TRUE.equals(redisTemplate.hasKey(tokenValue))
        && Objects.nonNull(userId)
        && new Date().before(claims.getExpiration())) {
      Optional<AuthenticationInfo> authenticationInfoOptional =
          authRepository.findById(Long.valueOf(userId));
      if (authenticationInfoOptional.isPresent())
        return AuthDto.builder()
            .isValid(true)
            .userId(Long.parseLong(userId))
            .role(authenticationInfoOptional.get().getRole())
            .build();
    }
    return AuthDto.builder().isValid(false).build();
  }

  public void invalidateToken(String token) {
    if (Boolean.TRUE.equals(redisTemplate.hasKey(token))) {
      redisTemplate.delete(token);
    }
  }

  private String generateToken(Long userId) {
    long nowMillis = System.currentTimeMillis();
    long expMillis = nowMillis + 24 * 60 * 60 * 1000;
    String token = generateTokenValue(userId, expMillis, nowMillis);
    redisTemplate.opsForValue().set(token, userId, expMillis, TimeUnit.MILLISECONDS);
    return token;
  }

  private String generateTokenValue(Long userId, long expMillis, long nowMillis) {
    return Jwts.builder()
        .setSubject(String.valueOf(userId))
        .setIssuedAt(new Date(nowMillis))
        .setExpiration(new Date(expMillis))
        .setId(UUID.randomUUID().toString())
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public void register(String username, String password, Role role) {
    AuthenticationInfo existingUser = authRepository.findByUsername(username);
    if (Objects.nonNull(existingUser)) {
      log.info("User already exists: {}", existingUser);
      throw new CustomException(ErrorCode.USER_ALREADY_EXISTS, "User already exists");
    }
    String encodedPassword = passwordEncoder.encode(password);

    AuthenticationInfo authenticationInfo =
        authRepository.save(
            AuthenticationInfo.builder()
                .username(username)
                .password(encodedPassword)
                .role(role)
                .build());

    switch (role) {
      case CUSTOMER ->
          userApi.createCustomer(CustomerDto.builder().id(authenticationInfo.getUserId()).build());
      case DELIVERY_PERSON ->
          userApi.createDeliveryPerson(
              DeliveryPersonDto.builder().id(authenticationInfo.getUserId()).build());
    }
  }
}
