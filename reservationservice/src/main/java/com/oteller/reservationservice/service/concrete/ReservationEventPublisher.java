package com.oteller.reservationservice.service.concrete;

import com.oteller.reservationservice.service.model.ReservationCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationEventPublisher {

    private final KafkaTemplate<String, ReservationCreatedEvent> kafkaTemplate;

    @Value("${reservation.kafka.topic}")
    private String reservationCreatedTopic;

    public void publishReservationCreated(ReservationCreatedEvent event) {
        kafkaTemplate.send(reservationCreatedTopic, event);
    }
}
