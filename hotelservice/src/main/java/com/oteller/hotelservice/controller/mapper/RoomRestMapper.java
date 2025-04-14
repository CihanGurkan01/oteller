package com.oteller.hotelservice.controller.mapper;

import com.oteller.hotelservice.controller.model.request.RoomRequestDto;
import com.oteller.hotelservice.service.model.RoomDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring") // Spring ile auto-inject edilebilir hale gelir
public interface RoomRestMapper {
    RoomRestMapper INSTANCE = Mappers.getMapper(RoomRestMapper.class);

    RoomDto toRoomDto(RoomRequestDto roomRequestDto);

}