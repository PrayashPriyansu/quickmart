package com.quickmart.inventory_service.mapper;

import com.quickmart.inventory_service.dto.ProductStockResponse;
import com.quickmart.inventory_service.entity.ProductStock;
import org.springframework.stereotype.Component;

@Component
public class ProductStockMapper {

    public ProductStockResponse toDto(ProductStock productStock){

        return new ProductStockResponse(
                productStock.getId(),
                productStock.getSkuId(),
                productStock.getStoreId(),
                productStock.getQuantityAvailable(),
                productStock.getQuantityReserved()
        );
    }
}
