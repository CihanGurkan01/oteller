package com.oteller.hotelservice.service.validation;

import com.oteller.hotelservice.data.repository.HotelRepository;
import com.oteller.hotelservice.data.repository.RoomRepository;
import com.oteller.hotelservice.result.ErrorResultImpl;
import com.oteller.hotelservice.result.Result;
import com.oteller.hotelservice.result.SuccessResultImpl;
import com.oteller.hotelservice.service.model.RoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:12.04.2025
 */
@Component
@RequiredArgsConstructor
public class RoomValidationStrategy extends AbstractValidationStrategy<RoomDto> {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;



    @Override
    public Result isValidateForCreate(RoomDto roomDto) {
        Result result = checkRoomDtoFields(roomDto);
        if(!result.success()){
            return result;
        }

        if (roomRepository.existsByHotelIdAndRoomNumber(roomDto.getHotelId(), roomDto.getRoomNumber())) {
            return new ErrorResultImpl("The same room number already exists in this hotel.");
        }

        return new SuccessResultImpl();
    }

    private Result checkRoomDtoFields(RoomDto roomDto){
        Result nullCheck = checkNull(roomDto, "Room data is required.");
        if (nullCheck != null) return nullCheck;

        if (isNullOrEmpty(roomDto.getHotelId()) ||
                isNullOrEmpty(hotelRepository.findById(roomDto.getHotelId()))) {
            return new ErrorResultImpl("Hotel id is null or hotel not found.");
        }

        if (isNullOrEmpty(roomDto.getRoomNumber())) {
            return new ErrorResultImpl("Room number is required.");
        }

        if (isNullOrEmpty(roomDto.getCapacity())) {
            return new ErrorResultImpl("Room capacity is required.");
        }

        if (isNullOrEmpty(roomDto.getPricePerNight())) {
            return new ErrorResultImpl("Room price per night is required.");
        }
        return new SuccessResultImpl();
    }

    @Override
    public Result isValidateForUpdate(RoomDto roomDto) {

        Result validateForDelete = isValidateForDelete(roomDto);
        if(!validateForDelete.success()){
            return validateForDelete;
        }

        if (isNullOrEmpty(roomDto.getHotelId()) ||
                isNullOrEmpty(hotelRepository.findById(roomDto.getHotelId()))) {
            return new ErrorResultImpl("Hotel id is required.");
        }

        if (roomRepository.existsByHotelIdAndRoomNumberAndIdNot(roomDto.getHotelId(), roomDto.getRoomNumber(), roomDto.getId())) {
            return new ErrorResultImpl("Another room with the same number already exists in this hotel.");
        }

        return isValidateForCreate(roomDto);
    }

    @Override
    public Result isValidateForDelete(RoomDto roomDto) {
        Result nullCheck = checkNull(roomDto, "Room data is required.");
        if (nullCheck != null) return nullCheck;

        if (isNullOrEmpty(roomDto.getId())) {
            return new ErrorResultImpl("If you want to delete this room, you must set id field");
        }

        if(!roomRepository.existsById(roomDto.getId())){
            return new ErrorResultImpl("Room with this id does not exist.");
        }

        return new SuccessResultImpl();
    }
}
