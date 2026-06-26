package com.quickmart.inventory_service.service;

import com.quickmart.inventory_service.dto.CreateProductStockRequest;
import com.quickmart.inventory_service.dto.ProductStockRequest;
import com.quickmart.inventory_service.dto.ProductStockResponse;
import com.quickmart.inventory_service.entity.ProductStock;

import java.util.List;
import java.util.UUID;

public interface ProductStockService {
    ProductStockResponse createProductStock(CreateProductStockRequest request);

    ProductStockResponse addStock(ProductStockRequest request);

    ProductStockResponse getStock(UUID skuId, UUID storeId);

    List<ProductStockResponse> getAllStocksOfSku(UUID skuId);

    void reserve(UUID skuId, UUID storeId, int quantity);
    void releaseReservation(UUID skuId, UUID storeId, int quantity);
    void confirmDeduction(UUID skuId, UUID storeId, int quantity);
}