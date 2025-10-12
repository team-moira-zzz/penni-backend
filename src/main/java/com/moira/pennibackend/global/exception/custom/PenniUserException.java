package com.moira.pennibackend.global.exception.custom;

import com.moira.pennibackend.global.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PenniUserException extends RuntimeException {
    private final ErrorCode errorCode;
    private final HttpStatus httpStatus;

    public PenniUserException(ErrorCode errorCode, HttpStatus httpStatus) {
        super(errorCode.getMessage());

        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
}
