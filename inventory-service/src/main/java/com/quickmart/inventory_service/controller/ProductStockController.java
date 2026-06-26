package com.quickmart.inventory_service.controller;

import com.quickmart.inventory_service.dto.CreateProductStockRequest;
import com.quickmart.inventory_service.dto.ProductStockRequest;
import com.quickmart.inventory_service.dto.ProductStockResponse;
import com.quickmart.inventory_service.service.ProductStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/inventory/stocks")
@RequiredArgsConstructor
public class ProductStockController {

    private final ProductStockService productStockService;

    @PostMapping()
    public ResponseEntity<ProductStockResponse> createStock(@RequestBody @Valid CreateProductStockRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productStockService.createProductStock(request));
    }

    @PatchMapping("add")
    public ResponseEntity<ProductStockResponse> addStock(@RequestBody @Valid ProductStockRequest request) {
        return ResponseEntity.ok(productStockService.addStock(request));
    }
    @GetMapping()
    public ResponseEntity<?> getStock(
            @RequestParam(required = false) UUID skuId,
            @RequestParam(required = false) UUID storeId) {

        if (skuId != null && storeId != null) {
            return ResponseEntity.ok(productStockService.getStock(skuId, storeId));
        } else if (skuId != null) {
            return ResponseEntity.ok(productStockService.getAllStocksOfSku(skuId));
        }
        return ResponseEntity.badRequest().body("Provide at least skuId");
    }
}