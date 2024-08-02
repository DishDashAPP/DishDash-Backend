package com.dish_dash.gateway.annotation.aspect;

import static java.util.Map.entry;

import com.dishDash.common.dto.AuthDto;
import com.dishDash.common.enums.ErrorCode;
import com.dishDash.common.enums.Role;
import com.dishDash.common.exception.CustomException;
import com.dishDash.common.feign.authentication.AuthenticationApi;
import com.dishDash.common.util.HttpHeaders;
import com.dish_dash.gateway.annotation.Authentication;
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
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
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
      Map.ofEntries(
          entry("/v1/payment", List.of(Role.USER, Role.CUSTOMER)),
          entry("/v1/rate", List.of(Role.USER, Role.CUSTOMER)),
          entry("/v1/review/order", List.of(Role.USER, Role.CUSTOMER)),
          entry(
              "/v1/auth",
              List.of(Role.USER, Role.CUSTOMER, Role.RESTAURANT_OWNER, Role.DELIVERY_PERSON)),
          entry("/user", List.of(Role.USER, Role.CUSTOMER)),
          entry("/v1/customer", List.of(Role.USER, Role.CUSTOMER)),
          entry("/v1/order/customer", List.of(Role.USER, Role.CUSTOMER)),
          entry("/v1/shoppingCart/customer", List.of(Role.USER, Role.CUSTOMER)),
          entry("/v1/food", List.of(Role.RESTAURANT_OWNER)),
          entry("/v1/category", List.of(Role.RESTAURANT_OWNER)),
          entry("/v1/restaurantOwner", List.of(Role.RESTAURANT_OWNER)),
          entry("/v1/restaurantOwner/all", List.of(Role.CUSTOMER)),
          entry("/v1/order/restaurantOwner", List.of(Role.RESTAURANT_OWNER)),
          entry("/v1/deliveryPerson", List.of(Role.DELIVERY_PERSON)),
          entry("/v1/order/deliveryPerson", List.of(Role.DELIVERY_PERSON)),
          entry("/v1/menu", List.of(Role.DELIVERY_PERSON)));

  @Around("@annotation(authentication)")
  public Object authentication(ProceedingJoinPoint joinPoint, Authentication authentication)
      throws Throwable {
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    String[] parameterNames = methodSignature.getParameterNames();
    Object[] args = joinPoint.getArgs();
    Optional<HttpServletRequest> httpServletRequestOptional = getRequest();

    ExpressionParser parser = new SpelExpressionParser();
    StandardEvaluationContext context = new StandardEvaluationContext();

    for (int i = 0; i < parameterNames.length; i++) {
      context.setVariable(parameterNames[i], args[i]);
    }

    String tokenExpression = authentication.token();
    String userIdExpression = authentication.userId();
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

    if (!isAccessAllowed(authDto.getRole(), requestPath)) {
      log.error(
          "Access denied for user with roles: {} to path: {}", authDto.getRole(), requestPath);
      throw new CustomException(ErrorCode.FORBIDDEN, "Access denied");
    }

    if (!tokenExpression.isBlank()) {
      parser.parseExpression(tokenExpression).setValue(context, token);
    }
    if (!userIdExpression.isBlank()) {
      parser.parseExpression(userIdExpression).setValue(context, authDto.getUserId());
    }
    for (int i = 0; i < parameterNames.length; i++) {
      args[i] = parser.parseExpression("#" + parameterNames[i]).getValue(context);
    }

    return joinPoint.proceed(args);
  }

  private boolean isAccessAllowed(Role role, String requestPath) {
    return ROLE_PATH_ACCESS.entrySet().stream()
        .filter(entry -> requestPath.startsWith(entry.getKey()))
        .flatMap(entry -> entry.getValue().stream())
        .anyMatch(allowedRole -> allowedRole.equals(role));
  }

  private Optional<HttpServletRequest> getRequest() {
    return Optional.ofNullable(
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
        .map(ServletRequestAttributes::getRequest);
  }
}
