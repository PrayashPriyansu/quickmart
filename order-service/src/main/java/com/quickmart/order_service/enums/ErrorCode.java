package com.quickmart.order_service.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    ORDER_NOT_FOUND(
            "ORDER_NOT_FOUND",
            "Order not found.",
            HttpStatus.NOT_FOUND
    ),

    INVALID_ORDER_TRANSITION(
            "INVALID_ORDER_TRANSITION",
            "Cannot perform this action on the current order state.",
            HttpStatus.CONFLICT
    ),

    ORDER_ALREADY_DELIVERED(
            "ORDER_ALREADY_DELIVERED",
            "Order has already been delivered.",
            HttpStatus.CONFLICT
    ),

    ORDER_ALREADY_CANCELLED(
            "ORDER_ALREADY_CANCELLED",
            "Order has already been cancelled.",
            HttpStatus.CONFLICT
    );

    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}