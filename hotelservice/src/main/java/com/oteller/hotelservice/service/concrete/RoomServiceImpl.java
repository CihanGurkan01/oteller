package com.oteller.hotelservice.service.concrete;

import com.oteller.hotelservice.controller.model.response.RoomResponseDto;
import com.oteller.hotelservice.data.model.RoomEntity;
import com.oteller.hotelservice.data.repository.RoomRepository;
import com.oteller.hotelservice.result.DataResult;
import com.oteller.hotelservice.result.Result;
import com.oteller.hotelservice.result.SuccessResultImpl;
import com.oteller.hotelservice.result.SuccessfulDataResultImpl;
import com.oteller.hotelservice.service.RoomService;
import com.oteller.hotelservice.service.mapper.RoomMapper;
import com.oteller.hotelservice.service.model.RoomDto;
import com.oteller.hotelservice.service.validation.RoomValidationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:11.04.2025
 */
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper MAPPER = RoomMapper.INSTANCE;
    private final RoomValidationStrategy roomValidationStrategy;

    @Override
    public Result create(RoomDto roomDto) {
        Result result = roomValidationStrategy.isValidateForCreate(roomDto);
        if(!result.success()){
            return result;
        }
        roomRepository.save(MAPPER.toRoomEntity(roomDto));
        return new SuccessResultImpl("Room created successfully");    }

    @Override
    public Result update(RoomDto roomDto) {
        Result result = roomValidationStrategy.isValidateForUpdate(roomDto);
        if(!result.success()){
            return result;
        }
        RoomEntity roomEntity = roomRepository.findById(roomDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found with id: " + roomDto.getId()));
        MAPPER.toRoomEntityWithoutNull(roomDto,roomEntity);
        roomRepository.save(roomEntity);
        return new SuccessResultImpl("Room updated successfully");
    }


    @Override
    public Result deletePermanently(RoomDto roomDto) {
        Result result = roomValidationStrategy.isValidateForDelete(roomDto);
        if(!result.success()){
            return result;
        }
        roomRepository.delete(MAPPER.toRoomEntity(roomDto));
        return new SuccessResultImpl("Room deleted with permanently successfully");
    }

    @Override
    public DataResult<Page<RoomResponseDto>> findAll(Pageable pageable) {
        return new SuccessfulDataResultImpl<>(MAPPER.toRoomResponseDto(roomRepository.findAll(pageable)));
    }
}
