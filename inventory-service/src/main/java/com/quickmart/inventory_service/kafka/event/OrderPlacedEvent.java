package com.quickmart.inventory_service.dto;

import java.util.Map;
import java.util.UUID;

public record OrderPlacedEvent(
        UUID customerId,
        Map<UUID, Integer> items,
        String deliveryAddress
) {
}
