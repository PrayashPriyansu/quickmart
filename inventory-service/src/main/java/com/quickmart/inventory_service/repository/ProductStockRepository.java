package com.quickmart.inventory_service.repository;

import com.quickmart.inventory_service.entity.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductStockRepository extends JpaRepository<ProductStock, UUID> {

    Optional<ProductStock> findBySkuIdAndStoreId(UUID skuId, UUID storeId);
    List<ProductStock> findBySkuId(UUID skuId);
}
