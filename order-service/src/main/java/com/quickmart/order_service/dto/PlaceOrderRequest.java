package com.quickmart.order_service.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Map;
import java.util.UUID;

public record PlaceOrderRequest(
        @NotNull
        UUID customerId,

        @NotNull
        @NotEmpty
        Map<@NotNull UUID, @Positive Integer> items,

        @NotBlank
        String deliveryAddress
) {
}
