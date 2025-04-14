package com.oteller.hotelservice.controller.concrete;

import com.oteller.hotelservice.controller.ActionResult;
import com.oteller.hotelservice.controller.HotelController;
import com.oteller.hotelservice.controller.RoomController;
import com.oteller.hotelservice.controller.abstracts.AbstractController;
import com.oteller.hotelservice.controller.mapper.HotelRestMapper;
import com.oteller.hotelservice.controller.mapper.RoomRestMapper;
import com.oteller.hotelservice.controller.model.request.HotelRequestDto;
import com.oteller.hotelservice.controller.model.request.RoomRequestDto;
import com.oteller.hotelservice.controller.model.response.RoomResponseDto;
import com.oteller.hotelservice.result.DataResult;
import com.oteller.hotelservice.result.Result;
import com.oteller.hotelservice.service.HotelService;
import com.oteller.hotelservice.service.RoomService;
import com.oteller.hotelservice.service.model.HotelDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:11.04.2025
 */
@RestController
@RequiredArgsConstructor
public class RoomControllerImpl extends AbstractController implements RoomController {

    private final RoomService roomService;
    private final RoomRestMapper MAPPER = RoomRestMapper.INSTANCE;

    @Override
    public ActionResult createRoom(RoomRequestDto roomRequestDto) {
        Result dataResult = roomService.create(MAPPER.toRoomDto(roomRequestDto));
        if (dataResult.success()) {
            return ok(dataResult.message());
        }

        return badRequest(dataResult.message());
    }

    @Override
    public ActionResult updateRoom(RoomRequestDto roomRequestDto) {
        Result dataResult = roomService.update(MAPPER.toRoomDto(roomRequestDto));
        if (dataResult.success()) {
            return ok(dataResult.message());
        }

        return badRequest(dataResult.message());
    }

    @Override
    public ActionResult deletePermanentlyRoom(RoomRequestDto roomRequestDto) {
        Result result = roomService.deletePermanently(MAPPER.toRoomDto(roomRequestDto));
        if (result.success()) {
            return ok(result.message());
        }

        return badRequest(result.message());
    }

    @Override
    public ActionResult findAllRoom(Pageable pageable) {
        DataResult<Page<RoomResponseDto>> dataResult = roomService.findAll(pageable);
        if (dataResult.success()) {
            return ok(dataResult.data().get());
        }

        return badRequest(dataResult.message());
    }
}
