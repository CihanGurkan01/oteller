package com.oteller.reservationservice.data.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:11.04.2025
 * Time:18:30
 */
@Entity
@Data
@Table(name = "reservation", schema = "public")
public class ReservationEntity extends BaseEntity {
    private Long hotelId;
    private Long roomId;
    private String guestName;
    private String userId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

}
