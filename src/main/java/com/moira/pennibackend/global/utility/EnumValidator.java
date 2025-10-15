package com.moira.pennibackend.global.utility;

import com.moira.pennibackend.global.exception.ErrorCode;
import com.moira.pennibackend.global.exception.custom.PenniGroupException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class EnumValidator {
    public <T extends Enum<T>> void validateStringValue(Class<T> enumClass, String inputValue, ErrorCode errorCode) {
        try {
            Enum.valueOf(enumClass, inputValue);
        } catch (IllegalArgumentException e) {
            throw new PenniGroupException(errorCode, HttpStatus.BAD_REQUEST);
        }
    }
}
