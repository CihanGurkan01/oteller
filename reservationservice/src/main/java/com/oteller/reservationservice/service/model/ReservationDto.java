package com.oteller.reservationservice.service.model;

import com.oteller.reservationservice.enumeration.StatusEnumeration;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:11.04.2025
 * Time:18:33
 */
@Data
@Builder
public class ReservationDto {
    private Long id;
    private Long hotelId;
    private Long roomId;
    private String guestName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

}
