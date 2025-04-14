package com.oteller.hotelservice.controller.model.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:11.04.2025
 */
@Data
@Builder
public class RoomResponseDto{
    private Long id;
    private Long hotelId;
    private String roomNumber;
    private Integer capacity;
    private BigDecimal pricePerNight;
    private String description;
}
