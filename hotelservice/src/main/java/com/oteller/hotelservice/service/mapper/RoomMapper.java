package com.oteller.hotelservice.service.mapper;

import com.oteller.hotelservice.controller.model.response.RoomResponseDto;
import com.oteller.hotelservice.data.model.HotelEntity;
import com.oteller.hotelservice.data.model.RoomEntity;
import com.oteller.hotelservice.service.model.HotelDto;
import com.oteller.hotelservice.service.model.RoomDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = "spring") // Spring ile auto-inject edilebilir hale gelir
public interface RoomMapper {
    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    RoomEntity toRoomEntity(RoomDto roomDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toRoomEntityWithoutNull(RoomDto dto, @MappingTarget RoomEntity entity);

    List<RoomDto> toRoomDto(List<RoomEntity> entities);

    @Mapping(target = "hotelId", source = "hotel.id")
    RoomResponseDto toRoomResponseDto(RoomEntity entity);

    List<RoomResponseDto> toRoomResponseDto(List<RoomEntity> entities);

    default Page<RoomDto> toRoomDto(Page<RoomEntity> page) {
        List<RoomDto> dtoList = toRoomDto(page.getContent());
        return new PageImpl<>(dtoList, page.getPageable(), page.getTotalElements());
    }

    default Page<RoomResponseDto> toRoomResponseDto(Page<RoomEntity> page) {
        List<RoomResponseDto> dtoList = toRoomResponseDto(page.getContent());
        return new PageImpl<>(dtoList, page.getPageable(), page.getTotalElements());
    }

    @AfterMapping
    default void setHotelReference(@MappingTarget RoomEntity room, RoomDto dto) {
        if (dto.getHotelId() != null) {
            HotelEntity hotel = new HotelEntity();
            hotel.setId(dto.getHotelId());
            room.setHotel(hotel);
        }
    }

}