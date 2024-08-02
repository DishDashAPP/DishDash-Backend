package com.dish_dash.gateway.exception;

import com.dishDash.common.exception.CustomException;
import feign.FeignException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdvice {

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<String> handleCustomException(CustomException ex) {
    log.warn(
        "Custom Exception. responding with error code: {}, exception_message: {}",
        ex.getErrorCode().getErrorCodeValue(),
        ex.getMessage());
    return new ResponseEntity<>(
        ex.getMessage(), HttpStatus.resolve(ex.getErrorCode().getErrorCodeValue()));
  }

  @ExceptionHandler(FeignException.class)
  public ResponseEntity<String> handleCustomException(FeignException ex) {
    log.warn(
        "FeignException. responding with error code: {}, exception_message: {}",
        ex.status(),
        ex.getMessage());
    return new ResponseEntity<>(
        ex.getMessage(), Objects.requireNonNull(HttpStatus.resolve(ex.status())));
  }
}
