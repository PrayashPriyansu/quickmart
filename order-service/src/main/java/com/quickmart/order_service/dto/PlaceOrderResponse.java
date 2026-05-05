package com.quickmart.order_service.dto;

import java.util.UUID;

public record PlaceOrderResponse(
        UUID id,
        String status
) {
}
