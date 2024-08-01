package com.dish_dash.authentication;

import com.dishDash.common.dto.AuthDto;
import com.dishDash.common.enums.Role;
import com.dishDash.common.exception.CustomException;
import com.dishDash.common.feign.user.UserApi;
import com.dishDash.common.response.LoginResponse;
import com.dish_dash.authentication.application.service.AuthenticationService;
import com.dish_dash.authentication.domain.model.AuthenticationInfo;
import com.dish_dash.authentication.infrastructure.repository.AuthenticationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static reactor.core.publisher.Mono.when;

@SpringBootTest
public class AuthenticationServiceIntegrationTest {

  @Autowired private AuthenticationService authenticationService;
  @Autowired private AuthenticationRepository authRepository;
  @Autowired private RedisTemplate<String, Long> redisTemplate;
  @Autowired private PasswordEncoder passwordEncoder;
  @MockBean private UserApi userApi;

  @BeforeEach
  void setUp() {
    authRepository.deleteAll();
    authRepository.flush();
  }

  @Test
  void login_ShouldReturnLoginResponse_WhenCredentialsAreValid() {
    AuthenticationInfo authInfo =
        AuthenticationInfo.builder()
            .username("validuser")
            .password(passwordEncoder.encode("password"))
            .role(Role.CUSTOMER)
            .build();
    authRepository.save(authInfo);

    LoginResponse response = authenticationService.login("validuser", "password");

    assertNotNull(response, "LoginResponse should not be null");
    assertEquals(Role.CUSTOMER, response.getRole(), "User role should be CUSTOMER");
    assertFalse(response.getToken().isEmpty(), "Token should be generated");
  }

  @Test
  void login_ShouldThrowException_WhenCredentialsAreInvalid() {
    AuthenticationInfo authInfo =
        AuthenticationInfo.builder()
            .username("invaliduser")
            .password(passwordEncoder.encode("password"))
            .role(Role.CUSTOMER)
            .build();
    authRepository.save(authInfo);

    CustomException exception =
        assertThrows(
            CustomException.class,
            () -> authenticationService.login("invaliduser", "wrongpassword"));

    assertEquals(
        "Invalid username or password",
        exception.getMessage(),
        "Exception message should indicate invalid credentials");
  }

  @Test
  void validateToken_ShouldReturnAuthDto_WhenTokenIsValid() {
    AuthenticationInfo authInfo =
        AuthenticationInfo.builder()
            .username("testuser")
            .password(passwordEncoder.encode("password"))
            .role(Role.CUSTOMER)
            .build();
    authInfo = authRepository.save(authInfo);

    String token = authenticationService.login("testuser", "password").getToken();

    AuthDto authDto = authenticationService.validateToken(token);

    assertNotNull(authDto, "AuthDto should not be null");
    assertTrue(authDto.isValid(), "AuthDto should be valid");
    assertEquals(authInfo.getUserId(), authDto.getUserId(), "User ID should match");
    assertEquals(authInfo.getRole(), authDto.getRole(), "Role should match");
  }

  @Test
  void validateToken_ShouldReturnInvalidAuthDto_WhenTokenIsInvalid() {
    String invalidToken = "invalid.token.value";

    AuthDto authDto = authenticationService.validateToken(invalidToken);

    assertNotNull(authDto, "AuthDto should not be null");
    assertFalse(authDto.isValid(), "AuthDto should be invalid");
  }

  @Test
  void register_ShouldCreateNewUser_WhenDataIsValid() {
    String username = "newuser";
    String password = "newpassword";
    Role role = Role.CUSTOMER;
    Mockito.doNothing().when(userApi).createCustomer(any());
    authenticationService.register(username, password, role);

    AuthenticationInfo savedUser = authRepository.findByUsername(username);
    assertNotNull(savedUser, "Saved user should not be null");
    assertTrue(
        passwordEncoder.matches(password, savedUser.getPassword()),
        "Password should match the encoded password");
    assertEquals(role, savedUser.getRole(), "Role should be CUSTOMER");
  }

  @Test
  void register_ShouldThrowException_WhenUserAlreadyExists() {
    AuthenticationInfo existingUser =
        AuthenticationInfo.builder()
            .username("existinguser")
            .password(passwordEncoder.encode("password"))
            .role(Role.CUSTOMER)
            .build();
    authRepository.save(existingUser);

    CustomException exception =
        assertThrows(
            CustomException.class,
            () -> authenticationService.register("existinguser", "newpassword", Role.CUSTOMER));

    assertEquals(
        "User already exists",
        exception.getMessage(),
        "Exception message should indicate user already exists");
  }

  @Test
  void invalidateToken_ShouldRemoveTokenFromRedis() {
    AuthenticationInfo authInfo =
        AuthenticationInfo.builder()
            .username("testuser")
            .password(passwordEncoder.encode("password"))
            .role(Role.CUSTOMER)
            .build();
    authRepository.save(authInfo);

    String token = authenticationService.login("testuser", "password").getToken();
    assertEquals(Boolean.TRUE, redisTemplate.hasKey(token), "Token should be stored in Redis");

    authenticationService.invalidateToken(token);

    assertNotEquals(
        Boolean.TRUE, redisTemplate.hasKey(token), "Token should be removed from Redis");
  }
}
