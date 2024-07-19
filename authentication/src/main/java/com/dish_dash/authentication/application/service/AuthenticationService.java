package com.dish_dash.authentication.application.service;

import com.dish_dash.authentication.domain.model.AuthenticationInfo;
import com.dish_dash.authentication.domain.model.Token;
import com.dish_dash.authentication.infrastructure.repository.AuthenticationRepository;
import com.dish_dash.authentication.infrastructure.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationRepository authRepository;
    private final TokenRepository tokenRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final StringRedisTemplate redisTemplate;

    private static final String SECRET_KEY = "your-256-bit-secret"; // Replace with your actual secret key

    public String login(String username, String password) {
        AuthenticationInfo authInfo = authRepository.findByUsername(username);
        if (authInfo != null && passwordEncoder.matches(password, authInfo.getPassword())) {
            Token token = generateToken();
            return token.getValue();
        }
        return null;
    }

    public boolean validateToken(String tokenValue) {
        Token token = tokenRepository.findById(tokenValue).orElse(null);
        if (token != null) {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseClaimsJws(token.getValue())
                    .getBody();
            String redisKey = "token:" + token.getValue();
            String storedToken = redisTemplate.opsForValue().get(redisKey);
            return storedToken == null && new Date().before(claims.getExpiration());
        }
        return false;
    }

    public boolean invalidateToken(String tokenValue) {
        Token token = tokenRepository.findById(tokenValue).orElse(null);
        if (token != null) {
            String redisKey = "token:" + token.getValue();
            redisTemplate.opsForValue().set(redisKey, "revoked", 24, TimeUnit.HOURS);
            return true;
        }
        return false;
    }

    private Token generateToken() {
        Token token = new Token();
        token.setValue(generateTokenValue());
        tokenRepository.save(token);
        return token;
    }

    private String generateTokenValue() {
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + 24 * 60 * 60 * 1000; // 1 day validity
        return Jwts.builder()
                .setIssuer("https://auth.example.com")
                .setSubject("user")
                .setAudience("https://api.example.com")
                .setIssuedAt(new Date(nowMillis))
                .setExpiration(new Date(expMillis))
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    public boolean register(String username, String password, String role) {
    // 1. Check if the username already exists
    AuthenticationInfo existingUser = authRepository.findByUsername(username);
    if (existingUser != null) {
        // Username already exists
        return false; // Or throw an exception
    }

    // 2. Encode the password
    String encodedPassword = passwordEncoder.encode(password);

    // 3. Create and save the AuthenticationInfo object
    AuthenticationInfo newUser = new AuthenticationInfo();
    newUser.setUsername(username);
    newUser.setPassword(encodedPassword);
    newUser.setRoles(role);
    authRepository.save(newUser);

    return true;
}
}