package com.oteller.reservationservice.controller.mapper;


import com.oteller.reservationservice.controller.model.request.ReservationRequestDto;
import com.oteller.reservationservice.service.model.ReservationDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring") // Spring ile auto-inject edilebilir hale gelir
public interface ReservationRestMapper {
    ReservationRestMapper INSTANCE = Mappers.getMapper(ReservationRestMapper.class);

    ReservationDto toReservationDto(ReservationRequestDto reservationRequestDto);
}