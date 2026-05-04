package com.quickmart.order_service.dto;

import java.util.UUID;

public record OrderEventResponse(
        UUID id,
        String status
) {
}
