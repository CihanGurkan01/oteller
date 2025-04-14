package com.oteller.hotelservice.data.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:11.04.2025
 * Time:18:30
 */
@Entity
@Data
@Table(name = "hotel", schema = "public")
public class HotelEntity extends BaseEntity {
    private String name;
    private Integer starRating;
    @Embedded
    private AddressEntity address;
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomEntity> rooms;
}
