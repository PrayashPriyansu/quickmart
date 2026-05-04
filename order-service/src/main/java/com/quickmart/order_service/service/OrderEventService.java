package com.quickmart.order_service.service;

import com.quickmart.order_service.dto.OrderEventRequest;
import com.quickmart.order_service.dto.OrderEventResponse;
import org.springframework.stereotype.Service;

@Service
public interface OrderEventService {

    OrderEventResponse placeOrder(OrderEventRequest orderEventRequest);
}
