package com.oteller.reservationservice.controller.model.request;

import lombok.Data;

import java.time.LocalDate;

/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:11.04.2025
 */
@Data
public class ReservationRequestDto {
    private Long id;
    private Long hotelId;
    private Long roomId;
    private String guestName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
