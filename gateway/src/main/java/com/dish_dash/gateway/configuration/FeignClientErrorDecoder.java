package com.dish_dash.gateway.configuration;

import com.dishDash.common.enums.ErrorCode;
import com.dish_dash.gateway.exception.CustomGatewayException;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FeignClientErrorDecoder implements ErrorDecoder {

  @Override
  public Exception decode(String methodKey, Response response) {
    String body = getBodyAsString(response);

    ErrorCode errorCode = mapResponseBodyToErrorCode(body);

    if (errorCode != null) {
      return new CustomGatewayException(errorCode);
    }

    return new Exception("Unexpected error: " + response.reason());
  }

  private String getBodyAsString(Response response) {
    try {
      if (response.body() != null) {
        return new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
      }
      return "No response body available";
    } catch (IOException e) {
      return "Unable to read response body";
    }
  }

  private ErrorCode mapResponseBodyToErrorCode(String body) {
    for (ErrorCode errorCode : ErrorCode.values()) {
      if (body.contains(errorCode.name())) {
        return errorCode;
      }
    }
    return null;
  }
}
