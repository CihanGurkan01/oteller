package com.oteller.reservationservice.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:11.04.2025
 */
@Entity
@Table(name = "room", schema = "public")
public class RoomEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private HotelEntity hotel;
    private String roomNumber;
    private Integer capacity;
    private BigDecimal pricePerNight;
    private String description;

}
