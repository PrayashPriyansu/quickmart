package com.quickmart.order_service.controller;

import com.quickmart.order_service.dto.PlaceOrderRequest;
import com.quickmart.order_service.dto.PlaceOrderResponse;
import com.quickmart.order_service.service.OrderEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderEventController {

    private final OrderEventService orderEventService;


    @GetMapping("/{aggregateId}")
    public ResponseEntity<List<PlaceOrderResponse>> getOrderEvents(@PathVariable UUID aggregateId) {
        return ResponseEntity.ok(orderEventService.getOrderEvents(aggregateId));
    }


    @PostMapping()
    public ResponseEntity<PlaceOrderResponse> placeOrder(
            @RequestBody PlaceOrderRequest request
            ){

        PlaceOrderResponse placedOrder = orderEventService.placeOrder(request);


        return ResponseEntity
                .ok(placedOrder);
    }
}
