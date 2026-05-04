package com.quickmart.order_service.service.impl;

import com.quickmart.order_service.OrderEventType;
import com.quickmart.order_service.dto.OrderEventRequest;
import com.quickmart.order_service.dto.PlaceOrderResponse;
import com.quickmart.order_service.event.OrderEvent;
import com.quickmart.order_service.mapper.OrderEventMapper;
import com.quickmart.order_service.repository.OrderEventRepository;
import com.quickmart.order_service.service.OrderEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderEventServiceImpl implements OrderEventService {

    private final OrderEventMapper orderEventMapper;
    private final OrderEventRepository orderEventRepository;

    @Override
    public PlaceOrderResponse placeOrder(OrderEventRequest request) {
        OrderEvent orderEvent = orderEventMapper.toEntity(UUID.randomUUID(), 1, OrderEventType.ORDER_PLACED,request);

        OrderEvent saved = orderEventRepository.save(orderEvent);
        return orderEventMapper.toResponse(saved);
    }
}
