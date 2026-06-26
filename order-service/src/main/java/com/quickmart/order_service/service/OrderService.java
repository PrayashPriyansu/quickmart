package com.quickmart.order_service.service;

import com.quickmart.order_service.dto.PlaceOrderRequest;
import com.quickmart.order_service.dto.PlaceOrderResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface OrderService {

    PlaceOrderResponse placeOrder(PlaceOrderRequest orderEventRequest);
    List<PlaceOrderResponse> getOrderEvents(UUID aggregateId);
    PlaceOrderResponse cancelOrder(UUID orderEventId);
}
