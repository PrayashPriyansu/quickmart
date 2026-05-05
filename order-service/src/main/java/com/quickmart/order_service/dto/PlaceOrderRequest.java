package com.quickmart.order_service.dto;

import java.util.List;
import java.util.UUID;

public record PlaceOrderRequest(
        String customerId,
        List<UUID> items
) {
}
