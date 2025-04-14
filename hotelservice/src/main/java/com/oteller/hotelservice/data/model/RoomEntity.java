package com.oteller.hotelservice.data.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:11.04.2025
 */
@Data
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
