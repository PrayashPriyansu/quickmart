package com.quickmart.inventory_service.exception;

import com.quickmart.inventory_service.enums.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;
    private final Object[] args;

    public BusinessException(ErrorCode errorCode, Object ... args) {
        super(getFormattedMessage(errorCode, args));
        this.errorCode = errorCode;
        this.args = args;
    }

    private static String getFormattedMessage(ErrorCode errorCode, Object[] args) {
        if (args != null && args.length > 0) {
            return String.format(errorCode.getMessage(), args);
        }

        return errorCode.getMessage();
    }
}
