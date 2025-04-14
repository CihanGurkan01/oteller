package com.oteller.hotelservice.controller;

import com.oteller.hotelservice.controller.model.request.RoomRequestDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/rooms")
public interface RoomController {

    @PostMapping("/create")
    ActionResult createRoom(@RequestBody RoomRequestDto roomRequestDto);

    @PutMapping("/update")
    ActionResult updateRoom(@RequestBody RoomRequestDto roomRequestDto);

    @DeleteMapping("/deletePermanently")
    ActionResult deletePermanentlyRoom(@RequestBody RoomRequestDto roomRequestDto);

    @GetMapping("/findAll")
    ActionResult findAllRoom(Pageable pageable);

}
