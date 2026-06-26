package com.quickmart.inventory_service.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    PRODUCT_NOT_FOUND(
            "PRODUCT_NOT_FOUND",
            "Product stock not found for given SKU and store.",
            HttpStatus.NOT_FOUND
    ),
    INSUFFICIENT_STOCK_EXCEPTION(
            "INSUFFICIENT_STOCK_EXCEPTION",
            "Insufficient stock for SKU %s, requested: %d",
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