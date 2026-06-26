package com.quickmart.inventory_service.service.impl;

import com.quickmart.inventory_service.dto.CreateProductStockRequest;
import com.quickmart.inventory_service.dto.ProductStockRequest;
import com.quickmart.inventory_service.dto.ProductStockResponse;
import com.quickmart.inventory_service.entity.ProductStock;
import com.quickmart.inventory_service.enums.ErrorCode;
import com.quickmart.inventory_service.exception.BusinessException;
import com.quickmart.inventory_service.mapper.ProductStockMapper;
import com.quickmart.inventory_service.repository.ProductStockRepository;
import com.quickmart.inventory_service.service.ProductStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductStockServiceImpl implements ProductStockService {

    private final ProductStockRepository repo;
    private final ProductStockMapper mapper;

    @Override
    public ProductStockResponse createProductStock(CreateProductStockRequest request) {
        int threshold = request.lowStockThreshold() != null ? request.lowStockThreshold() : 10;
        ProductStock stock = ProductStock.create(request.skuId(), request.storeId(), request.quantity(), threshold);
        ProductStock saved = repo.save(stock);
        return mapper.toDto(saved);
    }

    @Override
    public ProductStockResponse addStock(ProductStockRequest request) {
        ProductStock stock= findProductStock(request.skuId(), request.storeId());

        stock.addStock(request.quantity());
        repo.save(stock);
        return mapper.toDto(stock);
    }

    @Override
    public ProductStockResponse getStock(UUID skuId, UUID storeId) {

        return mapper.toDto(findProductStock(skuId, storeId));
    }

    @Override
    public List<ProductStockResponse> getAllStocksOfSku(UUID skuId){
        return repo.findBySkuId(skuId).stream().map(mapper::toDto).toList();
    }

    @Override
    public void reserve(UUID skuId, UUID storeId, int quantity) {
        ProductStock stock= findProductStock(skuId, storeId);

        stock.reserve(quantity);
        repo.save(stock);
    }

    @Override
    public void releaseReservation(UUID skuId, UUID storeId, int quantity) {
        ProductStock stock= findProductStock(skuId, storeId);

        stock.releaseReservation(quantity);
        repo.save(stock);
    }

    @Override
    public void confirmDeduction(UUID skuId, UUID storeId, int quantity) {
        ProductStock stock= findProductStock(skuId, storeId);

        stock.confirmDeduction(quantity);
        repo.save(stock);
    }

    private ProductStock findProductStock(UUID skuId, UUID storeId){
        return repo
                .findBySkuIdAndStoreId(skuId, storeId)
                .orElseThrow(()-> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
    }
}
