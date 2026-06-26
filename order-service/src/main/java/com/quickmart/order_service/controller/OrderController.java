package com.quickmart.order_service.controller;

import com.quickmart.order_service.dto.PlaceOrderRequest;
import com.quickmart.order_service.dto.PlaceOrderResponse;
import com.quickmart.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{aggregateId}")
    public ResponseEntity<List<PlaceOrderResponse>> getOrderEvents(@PathVariable UUID aggregateId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.getOrderEvents(aggregateId));
    }

    @PostMapping()
    public ResponseEntity<PlaceOrderResponse> placeOrder(
            @RequestBody @Valid PlaceOrderRequest request
            ){

        PlaceOrderResponse placedOrder = orderService.placeOrder(request);

        return ResponseEntity
                .ok(placedOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID orderId) {

        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }

}
