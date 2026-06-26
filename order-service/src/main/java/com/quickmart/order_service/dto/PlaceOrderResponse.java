package com.quickmart.order_service.dto;

import com.quickmart.order_service.enums.OrderEventType;

import java.util.UUID;

public record PlaceOrderResponse(
        UUID orderId,
        OrderEventType status
) {
}
