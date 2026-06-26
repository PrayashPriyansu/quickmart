package com.quickmart.inventory_service.entity;

import com.quickmart.inventory_service.enums.ErrorCode;
import com.quickmart.inventory_service.exception.BusinessException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "product_stock")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class ProductStock {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.BINARY)
    private UUID id;

    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "sku_id", nullable = false)
    private UUID skuId;

    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "store_id", nullable = false)
    private UUID storeId;

    @Column(name = "quantity_available", nullable = false)
    private int quantityAvailable = 0;

    @Column(name = "quantity_reserved", nullable = false)
    private int quantityReserved = 0;

    @Column(name = "low_stock_threshold", nullable = false)
    private int lowStockThreshold = 10;

    @Version
    private int version;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedDate;

    public static ProductStock create(UUID skuId, UUID storeId, int initialQuantity, int lowStockThreshold) {
        ProductStock stock = new ProductStock();
        stock.skuId = skuId;
        stock.storeId = storeId;
        stock.quantityAvailable = initialQuantity;
        stock.lowStockThreshold = lowStockThreshold;
        return stock;
    }

    public void addStock(int quantity) {
        this.quantityAvailable += quantity;
    }

    public void reserve(int quantity) {
        if (quantityAvailable - quantityReserved < quantity) {
            throw new BusinessException(ErrorCode.INSUFFICIENT_STOCK_EXCEPTION,skuId, quantity);
        }
        this.quantityReserved += quantity;
    }

    public void releaseReservation(int quantity) {
        this.quantityReserved = Math.max(0, quantityReserved - quantity);
    }

    public void confirmDeduction(int quantity) {
        this.quantityAvailable -= quantity;
        this.quantityReserved -= quantity;
    }
}
