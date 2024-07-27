package com.dishDash.common.exception;

import com.dishDash.common.enums.ErrorCode;

public class MoreThanOneOrderException extends RuntimeException {
    private final ErrorCode errorCode;

    public MoreThanOneOrderException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

}
