package com.quickmart.order_service.mapper;

import com.quickmart.order_service.enums.OrderEventType;
import com.quickmart.order_service.dto.PlaceOrderResponse;
import com.quickmart.order_service.entity.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@SuppressWarnings("RedundantCatch")
public class OrderEventMapper {

    public OrderEvent toEntity(UUID aggregateId, Integer version, OrderEventType orderEventType, String payload) {
        return new OrderEvent(aggregateId, version, orderEventType, payload);
    }

    public PlaceOrderResponse toResponse(OrderEvent orderEvent) {
        return new PlaceOrderResponse(orderEvent.getAggregateId(), orderEvent.getEventType());
    }
}
