package com.quickmart.order_service.service.impl;

import com.quickmart.order_service.enums.OrderEventType;
import com.quickmart.order_service.dto.PlaceOrderRequest;
import com.quickmart.order_service.dto.PlaceOrderResponse;
import com.quickmart.order_service.event.OrderEvent;
import com.quickmart.order_service.mapper.OrderEventMapper;
import com.quickmart.order_service.repository.OrderEventRepository;
import com.quickmart.order_service.service.OrderEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderEventServiceImpl implements OrderEventService {

    private final OrderEventMapper orderEventMapper;
    private final OrderEventRepository  orderEventRepository;

    @Override
    public PlaceOrderResponse placeOrder(PlaceOrderRequest request) {
        OrderEvent orderEvent = orderEventMapper.toEntity(UUID.randomUUID(), 1, OrderEventType.ORDER_PLACED,request);

        OrderEvent saved = orderEventRepository.save(orderEvent);
        return orderEventMapper.toResponse(saved);
    }

    @Override
    public List<PlaceOrderResponse> getOrderEvents(UUID aggregateId) {
        List<OrderEvent> events =orderEventRepository.findByAggregateIdOrderByVersionAsc(aggregateId);

        return events
                .stream()
                .map(orderEventMapper::toResponse)
                .toList();
    }
}
