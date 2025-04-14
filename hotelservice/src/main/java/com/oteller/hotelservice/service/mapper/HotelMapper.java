package com.oteller.hotelservice.service.mapper;

import com.oteller.hotelservice.controller.model.response.HotelResponseDto;
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
public interface HotelMapper {
    HotelMapper INSTANCE = Mappers.getMapper(HotelMapper.class);

    HotelEntity toHotelEntity(HotelDto hotelDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toHotelEntityWithoutNull(HotelDto dto, @MappingTarget HotelEntity entity);


    List<HotelDto> toHotelDto(List<HotelEntity> entities);

    @Mapping(target = "hotelId", source = "hotel.id")
    RoomDto toRoomDto(RoomEntity entity);

    List<HotelResponseDto> toRoomDto(List<HotelEntity> entities);


    default Page<HotelDto> toHotelDto(Page<HotelEntity> page) {
        List<HotelDto> dtoList = toHotelDto(page.getContent());
        return new PageImpl<>(dtoList, page.getPageable(), page.getTotalElements());
    }

    default Page<HotelResponseDto> toRoomDto(Page<HotelEntity> page) {
        List<HotelResponseDto> dtoList = toRoomDto(page.getContent());
        return new PageImpl<>(dtoList, page.getPageable(), page.getTotalElements());
    }

    @AfterMapping
    default void setHotelToRooms(@MappingTarget HotelEntity hotel) {
        if (hotel.getRooms() != null) {
            for (RoomEntity room : hotel.getRooms()) {
                room.setHotel(hotel);
            }
        }
    }
}