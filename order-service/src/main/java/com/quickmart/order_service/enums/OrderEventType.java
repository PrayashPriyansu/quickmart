package com.quickmart.order_service.enums;

public enum OrderEventType {
    ORDER_PLACED,           // customer submitted order
    STOCK_RESERVED,         // inventory confirmed stock
    STOCK_RESERVATION_FAILED,
    PAYMENT_COMPLETED,
    PAYMENT_FAILED,
    ORDER_CONFIRMED,        // all checks passed, order is live
    ORDER_CANCELLED,        // terminal failure state
    PICKER_ASSIGNED,        // fulfillment started
    ORDER_DISPATCHED,       // out for delivery
    ORDER_DELIVERED         // terminal success state
}
