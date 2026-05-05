package com.quickmart.order_service.service;

import com.quickmart.order_service.dto.PlaceOrderRequest;
import com.quickmart.order_service.dto.PlaceOrderResponse;
import org.springframework.stereotype.Service;

@Service
public interface OrderEventService {

    PlaceOrderResponse placeOrder(PlaceOrderRequest orderEventRequest);
}
