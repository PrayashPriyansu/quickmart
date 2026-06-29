package com.quickmart.order_service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quickmart.order_service.aggregate.Order;
import com.quickmart.order_service.enums.ErrorCode;
import com.quickmart.order_service.dto.PlaceOrderRequest;
import com.quickmart.order_service.dto.PlaceOrderResponse;
import com.quickmart.order_service.event.OrderEvent;
import com.quickmart.order_service.exception.BusinessException;
import com.quickmart.order_service.kafka.OrderEventProducer;
import com.quickmart.order_service.mapper.OrderEventMapper;
import com.quickmart.order_service.repository.OrderEventRepository;
import com.quickmart.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderEventMapper orderEventMapper;
    private final OrderEventRepository  orderEventRepository;
    private final ObjectMapper objectMapper;
    private final OrderEventProducer  orderEventProducer;

    @Override
    public PlaceOrderResponse placeOrder(PlaceOrderRequest request) {
        String payload;

        try {
            payload = objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize payload", e);
        }
        OrderEvent orderEvent = Order.place(request, payload);

        OrderEvent saved = orderEventRepository.save(orderEvent);

        orderEventProducer.publishOrderPlaced(saved.getAggregateId(), payload);

        return orderEventMapper.toResponse(saved);
    }

    @Override
    public List<PlaceOrderResponse> getOrderEvents(UUID aggregateId) {
        List<OrderEvent> events =orderEventRepository.findByAggregateIdOrderByVersionAsc(aggregateId);

        if (events.isEmpty()) {
            throw new BusinessException(ErrorCode.ORDER_NOT_FOUND);
        }

        return events
                .stream()
                .map(orderEventMapper::toResponse)
                .toList();
    }

    @Override
    public PlaceOrderResponse cancelOrder(UUID orderId) {
        Order order = reconstitute(orderId);
        OrderEvent event = order.cancel();
        OrderEvent saved = orderEventRepository.save(event);

        orderEventProducer.publishOrderCancelled(saved.getAggregateId());

        return orderEventMapper.toResponse(saved);
    }

    private Order reconstitute(UUID orderId) {
        List<OrderEvent> events = orderEventRepository
                .findByAggregateIdOrderByVersionAsc(orderId);

        if (events.isEmpty()) {
            throw new BusinessException(ErrorCode.ORDER_NOT_FOUND);
        }

        return Order.reconstitute(events);
    }
}
