package com.oteller.reservationservice.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCreatedEvent implements Serializable {
    private Long reservationId;
    private Long hotelId;
    private Long roomId;
    private String guestName;
    private String userId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
