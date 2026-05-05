package com.quickmart.order_service.mapper;

import com.quickmart.order_service.enums.OrderEventType;
import com.quickmart.order_service.dto.PlaceOrderRequest;
import com.quickmart.order_service.dto.PlaceOrderResponse;
import com.quickmart.order_service.event.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@SuppressWarnings("RedundantCatch")
public class OrderEventMapper {

    private final ObjectMapper objectMapper;

    public OrderEvent toEntity(UUID aggregateId, Integer version, OrderEventType orderEventType, PlaceOrderRequest orderEventRequest) {
        String payload;

        try{
            payload = objectMapper.writeValueAsString(orderEventRequest);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize order event payload", e);
        }


        return new OrderEvent(
                aggregateId,
                version,
                orderEventType.name(),
                payload
        );
    }

    public PlaceOrderResponse toResponse(OrderEvent orderEvent) {
        return new PlaceOrderResponse(orderEvent.getId(), orderEvent.getEventType());
    }
}
