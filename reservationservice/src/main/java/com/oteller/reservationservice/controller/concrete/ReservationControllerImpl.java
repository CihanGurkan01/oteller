package com.oteller.reservationservice.controller.concrete;

import com.oteller.reservationservice.controller.ActionResult;
import com.oteller.reservationservice.controller.ReservationController;
import com.oteller.reservationservice.controller.abstracts.AbstractController;
import com.oteller.reservationservice.controller.mapper.ReservationRestMapper;
import com.oteller.reservationservice.controller.model.request.ReservationRequestDto;
import com.oteller.reservationservice.controller.model.response.ReservationResponseDto;
import com.oteller.reservationservice.result.DataResult;
import com.oteller.reservationservice.result.Result;
import com.oteller.reservationservice.service.ReservationService;
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
public class ReservationControllerImpl extends AbstractController implements ReservationController {

    private final ReservationService reservationService;
    private final ReservationRestMapper MAPPER = ReservationRestMapper.INSTANCE;

    @Override
    public ActionResult createReservation(ReservationRequestDto reservationRequestDto) {
        Result dataResult = reservationService.create(MAPPER.toReservationDto(reservationRequestDto));
        if (dataResult.success()) {
            return ok(dataResult.message());
        }

        return badRequest(dataResult.message());
    }

    @Override
    public ActionResult updateReservation(ReservationRequestDto reservationRequestDto) {
        Result dataResult = reservationService.update(MAPPER.toReservationDto(reservationRequestDto));
        if (dataResult.success()) {
            return ok(dataResult.message());
        }

        return badRequest(dataResult.message());
    }

    @Override
    public ActionResult deletePermanentlyReservation(ReservationRequestDto reservationRequestDto) {
        Result result = reservationService.deletePermanently(MAPPER.toReservationDto(reservationRequestDto));
        if (result.success()) {
            return ok(result.message());
        }

        return badRequest(result.message());
    }

    @Override
    public ActionResult findAllReservation(Pageable pageable) {
        DataResult<Page<ReservationResponseDto>> dataResult = reservationService.findAll(pageable);
        if (dataResult.success()) {
            return ok(dataResult.data().get());
        }

        return badRequest(dataResult.message());
    }
}
