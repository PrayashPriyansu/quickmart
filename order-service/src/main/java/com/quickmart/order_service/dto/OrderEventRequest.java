package com.quickmart.order_service;

import java.util.List;
import java.util.UUID;

public record OrderEventRequest(
        String customerId,
        List<UUID> items
) {
}
