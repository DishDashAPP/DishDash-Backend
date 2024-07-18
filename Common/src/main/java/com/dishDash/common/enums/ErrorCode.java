package com.dishDash.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "Internal Server Error"),
  SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, 503, "Service Unavailable"),
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 401, "Unauthorized"),
  INVALID_TOKEN(HttpStatus.FORBIDDEN, 403, "Invalid Token"),
  BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "Bad Request"),
  NO_CONTENT(HttpStatus.NO_CONTENT, 204, "No Content");
  private final HttpStatus httpStatus;
  private final Integer errorCodeValue;
  private final String errorMessageValue;
}
