package com.dish_dash.gateway.annotation.aspect;

import com.dishDash.common.enums.ErrorCode;
import com.dishDash.common.feign.authentication.AuthenticationApi;
import com.dishDash.common.util.HttpHeaders;
import com.dish_dash.gateway.exception.CustomException;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuthenticationAspect {
  private final AuthenticationApi authenticationApi;

  @Around(
      "@annotation(com.dish_dash.gateway.annotation.Authentication) "
          + "|| within(@com.dish_dash.gateway.annotation.Authentication *)")
  public Object authentication(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    Optional<HttpServletRequest> httpServletRequestOptional = getRequest();

    if (httpServletRequestOptional.isEmpty()) {
      log.error("HttpServletRequest not found");
      throw new CustomException(ErrorCode.INVALID_TOKEN, "Invalid token");
    }

    String token = httpServletRequestOptional.get().getHeader(HttpHeaders.AUTHORIZATION);
    if (Objects.isNull(token)) {
      log.error("Authorization header is missing");
      throw new CustomException(ErrorCode.INVALID_TOKEN, "Invalid token");
    }

    String username = authenticationApi.validate(token);
    if (Objects.isNull(username)) {
      log.error("Invalid token: {}", token);
      throw new CustomException(ErrorCode.UNAUTHORIZED, "Unauthorized");
    }

    injectTokenIntoArgs(joinPoint, methodSignature, token, username);
    return joinPoint.proceed(joinPoint.getArgs());
  }

  private void injectTokenIntoArgs(
      ProceedingJoinPoint joinPoint,
      MethodSignature methodSignature,
      String token,
      String username) {
    Object[] args = joinPoint.getArgs();
    String[] parameterNames = methodSignature.getParameterNames();
    for (int i = 0; i < args.length; i++) {
      if ("token".equals(parameterNames[i]) && args[i] instanceof String) {
        log.info("Injecting token into method parameter: {}", parameterNames[i]);
        args[i] = token;
      } else if ("username".equals(parameterNames[i]) && args[i] instanceof String) {
        log.info("Injecting username into method parameter: {}", parameterNames[i]);
        args[i] = username;
      }
    }
  }

  private Optional<HttpServletRequest> getRequest() {
    return Optional.ofNullable(
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
        .map(ServletRequestAttributes::getRequest);
  }
}
