package com.oteller.hotelservice.controller.model.response;

import com.oteller.hotelservice.service.model.AddressDto;
import com.oteller.hotelservice.service.model.RoomDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:11.04.2025
 */
@Data
@Builder
public class HotelResponseDto {
    private Long id;
    private String name;
    private Integer starRating;
    private AddressDto address;
    private List<RoomDto> rooms;
}
