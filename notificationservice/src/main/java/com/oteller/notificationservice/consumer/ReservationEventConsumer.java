package com.oteller.notificationservice.consumer;

import com.oteller.notificationservice.model.ReservationCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ReservationEventConsumer {

    @KafkaListener(
            topics = "${reservation.kafka.topic}",
            groupId = "notification-service",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(ReservationCreatedEvent event) {
        log.info("Reservation event received: {}", event);
    }
}
