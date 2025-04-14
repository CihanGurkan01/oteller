package com.oteller.reservationservice.service.mapper;

import com.oteller.reservationservice.controller.model.response.ReservationResponseDto;
import com.oteller.reservationservice.data.model.ReservationEntity;
import com.oteller.reservationservice.service.model.ReservationDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = "spring") // Spring ile auto-inject edilebilir hale gelir
public interface ReservationMapper {
    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    ReservationEntity toReservationEntity(ReservationDto reservationDto);

    List<ReservationDto> toReservationDto(List<ReservationEntity> entities);

    List<ReservationResponseDto> toReservationResponseDto(List<ReservationEntity> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toReservationEntityWithoutNull(ReservationDto dto, @MappingTarget ReservationEntity entity);

    default Page<ReservationDto> toReservationDto(Page<ReservationEntity> page) {
        List<ReservationDto> dtoList = toReservationDto(page.getContent());
        return new PageImpl<>(dtoList, page.getPageable(), page.getTotalElements());
    }

    default Page<ReservationResponseDto> toReservationResponseDto(Page<ReservationEntity> page) {
        List<ReservationResponseDto> dtoList = toReservationResponseDto(page.getContent());
        return new PageImpl<>(dtoList, page.getPageable(), page.getTotalElements());
    }
}