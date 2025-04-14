package com.oteller.reservationservice.service.validation;

import com.oteller.reservationservice.data.model.ReservationEntity;
import com.oteller.reservationservice.data.repository.HotelRepository;
import com.oteller.reservationservice.data.repository.ReservationRepository;
import com.oteller.reservationservice.data.repository.RoomRepository;
import com.oteller.reservationservice.result.ErrorResultImpl;
import com.oteller.reservationservice.result.Result;
import com.oteller.reservationservice.result.SuccessResultImpl;
import com.oteller.reservationservice.service.model.ReservationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:12.04.2025
 */
@Component
@RequiredArgsConstructor
public class ReservationValidationStrategy extends AbstractValidationStrategy<ReservationDto> {
    private final ReservationRepository reservationRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    public Result isValidateForCreate(ReservationDto reservationDto) {
        Result result = checkReservationField(reservationDto);
        if(!result.success()){
            return result;
        }

        result = checkHotelId(reservationDto.getHotelId());
        if(!result.success()){
            return result;
        }

        result = checkRoomId(reservationDto.getRoomId(), reservationDto.getHotelId());
        if(!result.success()){
            return result;
        }

        if(reservationRepository.existsOverlappingReservation(
                reservationDto.getHotelId(),
                reservationDto.getRoomId(),
                reservationDto.getCheckInDate(),
                reservationDto.getCheckOutDate(),
                reservationDto.getId()
        )){
            return new ErrorResultImpl("This room is already reserved for the selected dates.");
        }

        return new SuccessResultImpl();
    }

    private Result checkHotelId(Long hotelId){
        if (isNullOrEmpty(hotelId) ||
                !hotelRepository.existsById(hotelId)) {
            return new ErrorResultImpl("Hotel id is required.");
        }
        return new SuccessResultImpl();
    }

    private Result checkRoomId(Long roomId, Long hotelId){
        if (isNullOrEmpty(roomId) ||
                !roomRepository.existsByHotelIdAndId(hotelId, roomId)) {
            return new ErrorResultImpl("Room id is required And This room have to register in this hotel.So We didn't find this room in this hotel.");
        }

        return new SuccessResultImpl();
    }

    private Result checkReservationField(ReservationDto reservationDto) {
        Result nullCheck = checkNull(reservationDto, "Reservation data is required.");
        if (nullCheck != null) return nullCheck;

        if (isNullOrEmpty(reservationDto.getHotelId())) {
            return new ErrorResultImpl("If you want to make a reservation, you must select hotelId field");
        }

        if (isNullOrEmpty(reservationDto.getRoomId())) {
            return new ErrorResultImpl("If you want to make a reservation, you must select roomId field");
        }

        if (isNullOrEmpty(reservationDto.getGuestName())) {
            return new ErrorResultImpl("If you want to make a reservation, you must set guest name field");
        }

        if (isNullOrEmpty(reservationDto.getCheckInDate())) {
            return new ErrorResultImpl("If you want to make a reservation, you must set check in date field");
        }

        if (isNullOrEmpty(reservationDto.getCheckOutDate())) {
            return new ErrorResultImpl("If you want to make a reservation, you must set check out date field");
        }
        return new SuccessResultImpl();
    }

    @Override
    public Result isValidateForUpdate(ReservationDto reservationDto) {
        if (isNullOrEmpty(reservationDto.getId())) {
            return new ErrorResultImpl("If you want to update a reservation, you must set id field");
        }

        Result result = checkReservationField(reservationDto);
        if(!result.success()){
            return result;
        }

        result = checkHotelId(reservationDto.getHotelId());
        if(!result.success()){
            return result;
        }

        result = checkRoomId(reservationDto.getRoomId(), reservationDto.getHotelId());
        if(!result.success()){
            return result;
        }

        if(reservationRepository.existsOverlappingReservation(
                reservationDto.getHotelId(),
                reservationDto.getRoomId(),
                reservationDto.getCheckInDate(),
                reservationDto.getCheckOutDate(),
                reservationDto.getId()
        )){
            return new ErrorResultImpl("This room is already reserved for the selected dates.");
        }


        return new SuccessResultImpl();
    }

    @Override
    public Result isValidateForDelete(ReservationDto reservationDto) {
        if (isNullOrEmpty(reservationDto.getId())) {
            return new ErrorResultImpl("If you want to delete a reservation, you must set id field");
        }
        ReservationEntity reservationEntity = reservationRepository.findById(reservationDto.getId())
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + reservationDto.getId()));
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!reservationEntity.getUserId().equalsIgnoreCase(userId)) {
            return new ErrorResultImpl("You are not authorized to delete this reservation.");
        }

        return new SuccessResultImpl();
    }
}
