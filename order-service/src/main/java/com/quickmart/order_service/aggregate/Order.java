package com.quickmart.order_service.aggregate;

import com.quickmart.order_service.dto.PlaceOrderRequest;
import com.quickmart.order_service.enums.ErrorCode;
import com.quickmart.order_service.enums.OrderStatus;
import com.quickmart.order_service.event.OrderEvent;
import com.quickmart.order_service.exception.BusinessException;
import lombok.Getter;

import java.util.UUID;

import static com.quickmart.order_service.enums.OrderEventType.*;

@Getter
public class Order {

    private UUID orderId;
    private UUID customerId;
    private OrderStatus status;
    private int currentVersion;

    // Private constructor — you never new up an Order directly
    private Order() {}

    // ─── Commands ──────────────────────────────────────────────────────────────
    // Commands are things you want to DO. They validate, then return an event.
    // They never touch the database.

    public static OrderEvent place(PlaceOrderRequest request, String payload) {
        UUID orderId = UUID.randomUUID();
        return new OrderEvent(orderId, 1, ORDER_PLACED, payload);
    }

    public OrderEvent cancel() {
        if (status == OrderStatus.DELIVERED) {
            throw new BusinessException(ErrorCode.ORDER_ALREADY_DELIVERED);
        }
        if (status == OrderStatus.CANCELLED) {
            throw new BusinessException(ErrorCode.ORDER_ALREADY_CANCELLED);
        }
        return new OrderEvent(orderId, currentVersion + 1, ORDER_CANCELLED, "{}");
    }

    // ─── Apply ─────────────────────────────────────────────────────────────────
    // Apply methods reconstruct state by replaying events.
    // One apply per event type. No validation here — events already happened.

    public void apply(OrderEvent event) {
        switch (event.getEventType()) {
            case ORDER_PLACED -> applyOrderPlaced(event);
            case ORDER_CANCELLED -> applyOrderCancelled();
            case STOCK_RESERVED -> applyStockReserved();
            case STOCK_RESERVATION_FAILED -> applyStockReservationFailed();
            case PAYMENT_COMPLETED -> applyPaymentCompleted();
            case PAYMENT_FAILED -> applyPaymentFailed();
            case ORDER_CONFIRMED -> applyOrderConfirmed();
            case PICKER_ASSIGNED -> applyPickerAssigned();
            case ORDER_DISPATCHED -> applyOrderDispatched();
            case ORDER_DELIVERED -> applyOrderDelivered();
        }
        this.currentVersion = event.getVersion();
    }

    private void applyOrderPlaced(OrderEvent event) {
        this.orderId = event.getAggregateId();
        this.status = OrderStatus.PLACED;
    }

    private void applyOrderCancelled() {
        this.status = OrderStatus.CANCELLED;
    }

    private void applyStockReserved() {
        this.status = OrderStatus.STOCK_RESERVED;
    }

    private void applyStockReservationFailed() {
        this.status = OrderStatus.CANCELLED;
    }

    private void applyPaymentCompleted() {
        this.status = OrderStatus.PAYMENT_COMPLETED;
    }

    private void applyPaymentFailed() {
        this.status = OrderStatus.CANCELLED;
    }

    private void applyOrderConfirmed() {
        this.status = OrderStatus.CONFIRMED;
    }

    private void applyPickerAssigned() {
        this.status = OrderStatus.BEING_PICKED;
    }

    private void applyOrderDispatched() {
        this.status = OrderStatus.DISPATCHED;
    }

    private void applyOrderDelivered() {
        this.status = OrderStatus.DELIVERED;
    }

    // ─── Reconstitute ──────────────────────────────────────────────────────────
    // This is how you rebuild an Order from its event history.
    // Service calls this before executing any command.

    public static Order reconstitute(java.util.List<OrderEvent> events) {
        Order order = new Order();
        events.forEach(order::apply);
        return order;
    }
}