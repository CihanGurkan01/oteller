package com.oteller.reservationservice.service.concrete;

import com.oteller.reservationservice.controller.model.response.ReservationResponseDto;
import com.oteller.reservationservice.data.model.ReservationEntity;
import com.oteller.reservationservice.data.model.RoomEntity;
import com.oteller.reservationservice.data.repository.ReservationRepository;
import com.oteller.reservationservice.data.repository.RoomRepository;
import com.oteller.reservationservice.result.*;
import com.oteller.reservationservice.service.ReservationService;
import com.oteller.reservationservice.service.mapper.ReservationMapper;
import com.oteller.reservationservice.service.model.ReservationCreatedEvent;
import com.oteller.reservationservice.service.model.ReservationDto;
import com.oteller.reservationservice.service.validation.ReservationValidationStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:11.04.2025
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final ReservationMapper MAPPER = ReservationMapper.INSTANCE;
    private final ReservationValidationStrategy reservationValidationStrategy;
    private final ReservationEventPublisher reservationEventPublisher;

    @Override
    @Transactional
    public Result create(ReservationDto reservationDto) {
        Result result = reservationValidationStrategy.isValidateForCreate(reservationDto);
        if(!result.success()){
            return result;
        }

        RoomEntity room = roomRepository.lockRoomById(reservationDto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        boolean conflict = reservationRepository.existsOverlappingReservation(
                reservationDto.getHotelId(),
                reservationDto.getRoomId(),
                reservationDto.getCheckInDate(),
                reservationDto.getCheckOutDate(),
                reservationDto.getId()
        );

        if (conflict) {
            return new ErrorResultImpl("Room is already reserved in this date range.");
        }

        ReservationEntity reservationEntity = MAPPER.toReservationEntity(reservationDto);
        reservationEntity.setUserId(SecurityContextHolder.getContext().getAuthentication().getName());
        reservationRepository.save(reservationEntity);

        try{
            reservationEventPublisher.publishReservationCreated(
                    ReservationCreatedEvent.builder()
                            .reservationId(reservationEntity.getId())
                            .hotelId(reservationEntity.getHotelId())
                            .roomId(reservationEntity.getRoomId())
                            .guestName(reservationEntity.getGuestName())
                            .checkInDate(reservationEntity.getCheckInDate())
                            .checkOutDate(reservationEntity.getCheckOutDate())
                            .build()
            );
        }catch (Exception e){
            log.error("Error while publishing reservation created event", e);
        }

        return new SuccessResultImpl("Reservation created successfully");
    }

    @Override
    @Transactional
    public Result update(ReservationDto reservationDto) {
        Result result = reservationValidationStrategy.isValidateForUpdate(reservationDto);
        if(!result.success()){
            return result;
        }

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        ReservationEntity foundReservation = reservationRepository.findByIdAndUserId(reservationDto.getId(), userId)
                .orElseThrow(() -> new RuntimeException("Reservation not found for user"));

        MAPPER.toReservationEntityWithoutNull(reservationDto, foundReservation);

        reservationRepository.save(foundReservation);
        return new SuccessResultImpl("Reservation updated successfully");
    }


    @Override
    @Transactional
    public Result deletePermanently(ReservationDto reservationDto) {
        Result result = reservationValidationStrategy.isValidateForDelete(reservationDto);
        if(!result.success()){
            return result;
        }
        reservationRepository.delete(MAPPER.toReservationEntity(reservationDto));
        return new SuccessResultImpl("Reservation deleted with permanently successfully");
    }

    @Override
    public DataResult<Page<ReservationResponseDto>> findAll(Pageable pageable) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return new SuccessfulDataResultImpl<>(MAPPER.toReservationResponseDto(reservationRepository.findAllByUserId(pageable,userId)));
    }
}
