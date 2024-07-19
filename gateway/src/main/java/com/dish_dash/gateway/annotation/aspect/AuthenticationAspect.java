package com.dish_dash.gateway.annotation.aspect;

import com.dishDash.common.dto.AuthDto;
import com.dishDash.common.enums.ErrorCode;
import com.dishDash.common.enums.Role;
import com.dishDash.common.exception.CustomException;
import com.dishDash.common.feign.authentication.AuthenticationApi;
import com.dishDash.common.util.HttpHeaders;
import java.util.List;
import java.util.Map;
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
  private static final Map<String, List<Role>> ROLE_PATH_ACCESS =
      Map.of(
          "/user", List.of(Role.USER, Role.CUSTOMER),
          "/restaurantOwner", List.of(Role.RESTAURANT_OWNER));

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

    AuthDto authDto = authenticationApi.validate(token);
    if (!authDto.isValid()) {
      log.error("Invalid token: {}", token);
      throw new CustomException(ErrorCode.UNAUTHORIZED, "Unauthorized");
    }
    String requestPath = httpServletRequestOptional.get().getRequestURI();

//    if (!isAccessAllowed(authDto.getRole(), requestPath)) {
//      log.error(
//          "Access denied for user with roles: {} to path: {}", authDto.getRole(), requestPath);
//      throw new CustomException(ErrorCode.FORBIDDEN, "Access denied");
//    }
    injectTokenIntoArgs(joinPoint, methodSignature, token, authDto.getUserId());
    return joinPoint.proceed(joinPoint.getArgs());
  }

  private boolean isAccessAllowed(Role role, String requestPath) {
    return ROLE_PATH_ACCESS.entrySet().stream()
        .filter(entry -> requestPath.startsWith(entry.getKey()))
        .flatMap(entry -> entry.getValue().stream())
        .anyMatch(allowedRole -> allowedRole.equals(role));
  }

  private void injectTokenIntoArgs(
      ProceedingJoinPoint joinPoint, MethodSignature methodSignature, String token, Long userId) {
    Object[] args = joinPoint.getArgs();
    String[] parameterNames = methodSignature.getParameterNames();
    for (int i = 0; i < args.length; i++) {
      if ("token".equals(parameterNames[i]) && args[i] instanceof String) {
        log.info("Injecting token into method parameter: {}", parameterNames[i]);
        args[i] = token;
      } else if ("userId".equals(parameterNames[i]) && args[i] instanceof Long) {
        log.info("Injecting userId into method parameter: {}", parameterNames[i]);
        args[i] = userId;
      }
    }
  }

  private Optional<HttpServletRequest> getRequest() {
    return Optional.ofNullable(
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
        .map(ServletRequestAttributes::getRequest);
  }
}
