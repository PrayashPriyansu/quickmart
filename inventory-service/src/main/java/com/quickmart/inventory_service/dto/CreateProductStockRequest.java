package com.quickmart.inventory_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

public record CreateProductStockRequest(

        @NotNull
        UUID skuId,

        @NotNull
        UUID storeId,

        @PositiveOrZero
        int quantity,

        @PositiveOrZero
        Integer lowStockThreshold
) {
}
