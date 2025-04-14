package com.oteller.hotelservice.controller.mapper;

import com.oteller.hotelservice.controller.model.request.HotelRequestDto;
import com.oteller.hotelservice.service.model.HotelDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring") // Spring ile auto-inject edilebilir hale gelir
public interface HotelRestMapper {
    HotelRestMapper INSTANCE = Mappers.getMapper(HotelRestMapper.class);

    HotelDto toHotelDto(HotelRequestDto hotelRequestDto);

}