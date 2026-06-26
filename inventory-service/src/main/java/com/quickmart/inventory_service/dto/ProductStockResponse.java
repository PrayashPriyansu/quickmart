package com.quickmart.inventory_service.dto;

import java.util.UUID;

public record ProductStockResponse(
        UUID id,
        UUID skuId,
        UUID storeId,
        int quantityAvailable,
        int quantityReserved
) {}