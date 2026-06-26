package com.quickmart.order_service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventProducer {

    private final KafkaTemplate<String, String> kafka;

    public void publishOrderPlaced(UUID orderId, String payload){
        kafka
                .send(KafkaTopics.ORDER_PLACED, orderId.toString(), payload)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to publish order.placed for orderId {}: {}", orderId, ex.getMessage());
                    } else {
                        log.info("Published order.placed for orderId {} to partition {} offset {}",
                                orderId,
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
            }
        });;
        log.info("Published order.placed for orderId: {}", orderId);
    }

    public void publishOrderCancelled(UUID orderId) {
        kafka
                .send(KafkaTopics.ORDER_CANCELLED, orderId.toString(), "{}")
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to publish order.cancelled for orderId {}: {}", orderId, ex.getMessage());
                    } else {
                        log.info("Published order.cancelled for orderId {} to partition {} offset {}",
                                orderId,
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });;
        log.info("Published order.cancelled for orderId: {}", orderId);
    }
}
