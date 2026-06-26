package com.quickmart.order_service.repository;

import com.quickmart.order_service.entity.OrderEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderEventRepository extends JpaRepository<OrderEvent, UUID> {

    List<OrderEvent> findByAggregateIdOrderByVersionAsc(UUID aggregateId);
    Optional<OrderEvent> findTopByAggregateIdOrderByVersionDesc(UUID aggregateId);
}
