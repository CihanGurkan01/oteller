package com.oteller.hotelservice.service.concrete;

import com.oteller.hotelservice.controller.model.response.HotelResponseDto;
import com.oteller.hotelservice.data.model.HotelEntity;
import com.oteller.hotelservice.data.repository.HotelRepository;
import com.oteller.hotelservice.result.DataResult;
import com.oteller.hotelservice.result.Result;
import com.oteller.hotelservice.result.SuccessResultImpl;
import com.oteller.hotelservice.result.SuccessfulDataResultImpl;
import com.oteller.hotelservice.service.HotelService;
import com.oteller.hotelservice.service.mapper.HotelMapper;
import com.oteller.hotelservice.service.model.HotelDto;
import com.oteller.hotelservice.service.validation.HotelValidationStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper MAPPER = HotelMapper.INSTANCE;
    private final HotelValidationStrategy hotelValidationStrategy;

    @Override
    @Transactional
    public Result create(HotelDto hotelDto) {
        Result result = hotelValidationStrategy.isValidateForCreate(hotelDto);
        if(!result.success()){
            return result;
        }
        hotelRepository.save(MAPPER.toHotelEntity(hotelDto));
        return new SuccessResultImpl("Hotel created successfully");
    }

    @Override
    @Transactional
    public Result update(HotelDto hotelDto) {
        Result result = hotelValidationStrategy.isValidateForUpdate(hotelDto);
        if(!result.success()){
            return result;
        }
        HotelEntity hotelEntity = hotelRepository.findById(hotelDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Hotel not found with id: " + hotelDto.getId()));
        MAPPER.toHotelEntityWithoutNull(hotelDto,hotelEntity);
        hotelRepository.save(hotelEntity);
        return new SuccessResultImpl("Hotel updated successfully");
    }


    @Override
    @Transactional
    public Result deletePermanently(HotelDto hotelDto) {
        Result result = hotelValidationStrategy.isValidateForDelete(hotelDto);
        if(!result.success()){
            return result;
        }
        hotelRepository.delete(MAPPER.toHotelEntity(hotelDto));
        return new SuccessResultImpl("Hotel deleted with permanently successfully");
    }

    @Override
    public DataResult<Page<HotelResponseDto>> findAll(Pageable pageable) {
        return new SuccessfulDataResultImpl<>(MAPPER.toRoomDto(hotelRepository.findAll(pageable)));
    }
}
