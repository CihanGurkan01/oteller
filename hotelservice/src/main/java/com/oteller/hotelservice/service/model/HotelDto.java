package com.oteller.hotelservice.service.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:11.04.2025
 * Time:18:33
 */
@Data
@Builder
public class HotelDto {
    private Long id;
    private String name;
    private Integer starRating;
    private AddressDto address;
    private List<RoomDto> rooms;
}
