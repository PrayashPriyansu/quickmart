package com.quickmart.order_service.event;

import com.quickmart.order_service.enums.OrderEventType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "order_events")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class OrderEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name  = "aggregate_id", nullable = false)
    private UUID aggregateId;

    @Version
    private Integer version;

    @Column(name = "event_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderEventType eventType;

    @Column(columnDefinition = "JSON", nullable = false)
    private String payload;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public OrderEvent(UUID aggregateId, Integer version, OrderEventType eventType, String payload) {
        this.aggregateId = aggregateId;
        this.version = version;
        this.eventType = eventType;
        this.payload = payload;
    }
}
