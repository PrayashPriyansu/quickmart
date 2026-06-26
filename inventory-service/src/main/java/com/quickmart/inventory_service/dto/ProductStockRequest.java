package com.quickmart.inventory_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record ProductStockRequest(
        @NotNull
        UUID skuId,
        @NotNull
        UUID storeId,
        @Positive
        int quantity
        ) {
}
