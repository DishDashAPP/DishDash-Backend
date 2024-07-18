package com.dishDash.common.authorization;

import com.dishDash.common.feign.authentication.AuthenticationApi;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

  private final AuthenticationApi authenticationApi;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    String token = request.getHeader("Authorization");
    if (token != null && !token.isEmpty()) {
      return Objects.nonNull(authenticationApi.validate(token));
    }
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    return false;
  }
}
