package com.oteller.hotelservice.controller.concrete;

import com.oteller.hotelservice.controller.ActionResult;
import com.oteller.hotelservice.controller.abstracts.AbstractController;
import com.oteller.hotelservice.controller.HotelController;
import com.oteller.hotelservice.controller.mapper.HotelRestMapper;
import com.oteller.hotelservice.controller.model.response.HotelResponseDto;
import com.oteller.hotelservice.result.DataResult;
import com.oteller.hotelservice.result.Result;
import com.oteller.hotelservice.service.HotelService;
import com.oteller.hotelservice.controller.model.request.HotelRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:11.04.2025
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class HotelControllerImpl extends AbstractController implements HotelController {

    private final HotelService hotelService;
    private final HotelRestMapper MAPPER = HotelRestMapper.INSTANCE;

    @Override
    public ActionResult createHotel(HotelRequestDto hotelRequestDto) {
        Result dataResult = hotelService.create(MAPPER.toHotelDto(hotelRequestDto));
        if (dataResult.success()) {
            return ok(dataResult.message());
        }

        return badRequest(dataResult.message());
    }

    @Override
    public ActionResult updateHotel(HotelRequestDto hotelRequestDto) {
        Result dataResult = hotelService.update(MAPPER.toHotelDto(hotelRequestDto));
        if (dataResult.success()) {
            return ok(dataResult.message());
        }

        return badRequest(dataResult.message());
    }

    @Override
    public ActionResult deletePermanentlyHotel(HotelRequestDto hotelRequestDto) {
        Result result = hotelService.deletePermanently(MAPPER.toHotelDto(hotelRequestDto));
        if (result.success()) {
            return ok(result.message());
        }

        return badRequest(result.message());
    }

    @Override
    public ActionResult findAllHotel(Pageable pageable) {
        DataResult<Page<HotelResponseDto>> dataResult = hotelService.findAll(pageable);
        if (dataResult.success()) {
            return ok(dataResult.data().get());
        }

        return badRequest(dataResult.message());
    }
}
