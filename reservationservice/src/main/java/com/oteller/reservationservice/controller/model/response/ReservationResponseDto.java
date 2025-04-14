package com.oteller.reservationservice.controller.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:11.04.2025
 */
@Data
@Builder
public class ReservationResponseDto {
    private Long id;
    private Long hotelId;
    private Long roomId;
    private String guestName;
    private Long userId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
