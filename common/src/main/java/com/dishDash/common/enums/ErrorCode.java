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
  FORBIDDEN(HttpStatus.FORBIDDEN, 403, "Forbidden"),
  INVALID_TOKEN(HttpStatus.FORBIDDEN, 403, "Invalid Token"),
  BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "Bad Request"),
  USER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, 400, "User already exists"),
  NO_CONTENT(HttpStatus.NO_CONTENT, 204, "No Content"),
  INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, 401, "Invalid credentials"),
  NOT_FOUND(HttpStatus.NOT_FOUND, 404, "Resource not found"),
  CONFLICT(HttpStatus.CONFLICT, 409, "Conflict"),
  UNPROCESSABLE_ENTITY(HttpStatus.UNPROCESSABLE_ENTITY, 422, "Unprocessable entity"),
  PAYMENT_REQUIRED(HttpStatus.PAYMENT_REQUIRED, 402, "Payment required"),
  METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, 405, "Method not allowed"),
  UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, 415, "Unsupported media type"),
  TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS, 429, "Too many requests");

  private final HttpStatus httpStatus;
  private final Integer errorCodeValue;
  private final String errorMessageValue;
}
