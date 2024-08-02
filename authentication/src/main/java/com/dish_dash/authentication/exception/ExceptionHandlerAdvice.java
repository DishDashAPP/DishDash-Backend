package com.dish_dash.authentication.exception;

import com.dishDash.common.exception.CustomException;
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
}
