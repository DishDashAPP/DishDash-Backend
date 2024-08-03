package com.dish_dash.gateway.exception;

import com.dishDash.common.enums.ErrorCode;
import com.dishDash.common.response.ErrorResponse;
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


  @ExceptionHandler(CustomGatewayException.class)
  public ResponseEntity<ErrorResponse> handleCustomGatewayException(CustomGatewayException ex) {
    ErrorCode errorCode = ex.getErrorCode();
    ErrorResponse errorResponse =
        new ErrorResponse(errorCode.getErrorCodeValue(), errorCode.getErrorMessageValue());

    return new ResponseEntity<>(errorResponse, errorCode.getClientHttpStatus());
  }
}
