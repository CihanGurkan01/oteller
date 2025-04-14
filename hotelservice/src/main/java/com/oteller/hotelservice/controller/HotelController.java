package com.oteller.hotelservice.controller;

import com.oteller.hotelservice.controller.model.request.HotelRequestDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/hotels")
public interface HotelController {

    @PostMapping("/create")
    ActionResult createHotel(@RequestBody HotelRequestDto hotelRequestDto);

    @PutMapping("/update")
    ActionResult updateHotel(@RequestBody HotelRequestDto hotelRequestDto);

    @DeleteMapping("/deletePermanently")
    ActionResult deletePermanentlyHotel(@RequestBody HotelRequestDto hotelRequestDto);

    @GetMapping("/findAll")
    ActionResult findAllHotel(Pageable pageable);

}
