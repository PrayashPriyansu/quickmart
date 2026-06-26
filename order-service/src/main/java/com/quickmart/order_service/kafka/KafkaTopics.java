package com.quickmart.order_service.kafka;

public final class KafkaTopics {

    private KafkaTopics() {}

    public static final String ORDER_PLACED = "order.placed";
    public static final String ORDER_CANCELLED = "order.cancelled";
}
