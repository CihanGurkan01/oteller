package com.oteller.reservationservice.controller;

import com.oteller.reservationservice.controller.model.request.ReservationRequestDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/reservations")
public interface ReservationController {

    @PostMapping("/create")
    ActionResult createReservation(@RequestBody ReservationRequestDto  reservationRequestDto);

    @PutMapping("/update")
    ActionResult updateReservation(@RequestBody ReservationRequestDto  reservationRequestDto);

    @DeleteMapping("/deletePermanently")
    ActionResult deletePermanentlyReservation(@RequestBody ReservationRequestDto  reservationRequestDto);

    @GetMapping("/findAll")
    ActionResult findAllReservation(Pageable pageable);

}
