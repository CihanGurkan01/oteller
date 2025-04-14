package com.oteller.hotelservice.controller.model.request;

import com.oteller.hotelservice.service.model.AddressDto;
import com.oteller.hotelservice.service.model.RoomDto;
import lombok.Data;

import java.util.List;

/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:11.04.2025
 */
@Data
public class HotelRequestDto {
    private Long id;
    private String name;
    //private String status;
    private AddressDto address;
    private Integer starRating;
    private List<RoomDto> rooms;
}
