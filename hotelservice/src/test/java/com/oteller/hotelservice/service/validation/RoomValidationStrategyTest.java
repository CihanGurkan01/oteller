package com.oteller.hotelservice.service.validation;

import com.oteller.hotelservice.data.repository.HotelRepository;
import com.oteller.hotelservice.data.repository.RoomRepository;
import com.oteller.hotelservice.result.ErrorResultImpl;
import com.oteller.hotelservice.result.Result;
import com.oteller.hotelservice.result.SuccessResultImpl;
import com.oteller.hotelservice.service.model.RoomDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RoomValidationStrategyTest {

    private RoomValidationStrategy strategy;
    private RoomRepository roomRepository;
    private HotelRepository hotelRepository;

    @BeforeEach
    void setup() {
        roomRepository = mock(RoomRepository.class);
        hotelRepository = mock(HotelRepository.class);
        strategy = new RoomValidationStrategy(roomRepository, hotelRepository);
    }

    @Test
    void shouldReturnErrorWhenRoomDtoIsNull_OnCreate() {
        Result result = strategy.isValidateForCreate(null);
        assertThat(result).isInstanceOf(ErrorResultImpl.class);
        assertThat(result.message()).contains("Room data is required.");
    }

    @Test
    void shouldReturnErrorWhenHotelIdIsNull_OnCreate() {
        RoomDto dto = RoomDto.builder()
                .roomNumber("101")
                .capacity(2)
                .pricePerNight(BigDecimal.TEN)
                .build();
        Result result = strategy.isValidateForCreate(dto);
        assertThat(result).isInstanceOf(ErrorResultImpl.class);
        assertThat(result.message()).contains("Hotel id is null or hotel not found.");
    }

    @Test
    void shouldReturnErrorWhenRoomNumberIsNull_OnCreate() {
        RoomDto dto = RoomDto.builder()
                .hotelId(1L)
                .capacity(2)
                .pricePerNight(BigDecimal.TEN)
                .build();
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(mock()));
        Result result = strategy.isValidateForCreate(dto);
        assertThat(result).isInstanceOf(ErrorResultImpl.class);
        assertThat(result.message()).contains("Room number is required.");
    }

    @Test
    void shouldReturnErrorWhenCapacityIsNull_OnCreate() {
        RoomDto dto = RoomDto.builder()
                .hotelId(1L)
                .roomNumber("101")
                .pricePerNight(BigDecimal.TEN)
                .build();
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(mock()));
        Result result = strategy.isValidateForCreate(dto);
        assertThat(result).isInstanceOf(ErrorResultImpl.class);
        assertThat(result.message()).contains("Room capacity is required.");
    }

    @Test
    void shouldReturnErrorWhenPriceIsNull_OnCreate() {
        RoomDto dto = RoomDto.builder()
                .hotelId(1L)
                .roomNumber("101")
                .capacity(2)
                .build();
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(mock()));
        Result result = strategy.isValidateForCreate(dto);
        assertThat(result).isInstanceOf(ErrorResultImpl.class);
        assertThat(result.message()).contains("Room price per night is required.");
    }

    @Test
    void shouldReturnErrorWhenRoomAlreadyExists_OnCreate() {
        RoomDto dto = RoomDto.builder()
                .hotelId(1L)
                .roomNumber("101")
                .capacity(2)
                .pricePerNight(BigDecimal.TEN)
                .build();
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(mock()));
        when(roomRepository.existsByHotelIdAndRoomNumber(1L, "101")).thenReturn(true);
        Result result = strategy.isValidateForCreate(dto);
        assertThat(result).isInstanceOf(ErrorResultImpl.class);
        assertThat(result.message()).contains("The same room number already exists in this hotel.");
    }

    @Test
    void shouldReturnSuccess_OnCreate() {
        RoomDto dto = RoomDto.builder()
                .hotelId(1L)
                .roomNumber("101")
                .capacity(2)
                .pricePerNight(BigDecimal.TEN)
                .build();
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(mock()));
        when(roomRepository.existsByHotelIdAndRoomNumber(1L, "101")).thenReturn(false);
        Result result = strategy.isValidateForCreate(dto);
        assertThat(result).isInstanceOf(SuccessResultImpl.class);
    }

    @Test
    void shouldReturnErrorWhenRoomDtoIsNull_OnUpdate() {
        Result result = strategy.isValidateForUpdate(null);
        assertThat(result).isInstanceOf(ErrorResultImpl.class);
        assertThat(result.message()).contains("Room data is required.");
    }

    @Test
    void shouldReturnErrorWhenRoomDoesNotExist_OnUpdate() {
        RoomDto dto = RoomDto.builder()
                .id(5L)
                .hotelId(1L)
                .roomNumber("101")
                .capacity(2)
                .pricePerNight(BigDecimal.TEN)
                .build();
        when(roomRepository.existsById(5L)).thenReturn(false);
        Result result = strategy.isValidateForUpdate(dto);
        assertThat(result).isInstanceOf(ErrorResultImpl.class);
        assertThat(result.message()).contains("Room with this id does not exist.");
    }

    @Test
    void shouldReturnErrorWhenHotelNotFound_OnUpdate() {
        RoomDto dto = RoomDto.builder()
                .id(5L)
                .hotelId(1L)
                .roomNumber("101")
                .capacity(2)
                .pricePerNight(BigDecimal.TEN)
                .build();
        when(roomRepository.existsById(5L)).thenReturn(true);
        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());
        Result result = strategy.isValidateForUpdate(dto);
        assertThat(result).isInstanceOf(ErrorResultImpl.class);
        assertThat(result.message()).contains("Hotel id is required.");
    }

    @Test
    void shouldReturnErrorWhenAnotherRoomWithSameNumberExists_OnUpdate() {
        RoomDto dto = RoomDto.builder()
                .id(5L)
                .hotelId(1L)
                .roomNumber("101")
                .capacity(2)
                .pricePerNight(BigDecimal.TEN)
                .build();
        when(roomRepository.existsById(5L)).thenReturn(true);
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(mock()));
        when(roomRepository.existsByHotelIdAndRoomNumberAndIdNot(1L, "101", 5L)).thenReturn(true);
        Result result = strategy.isValidateForUpdate(dto);
        assertThat(result).isInstanceOf(ErrorResultImpl.class);
        assertThat(result.message()).contains("Another room with the same number already exists in this hotel.");
    }

    @Test
    void shouldReturnSuccess_OnUpdate() {
        RoomDto dto = RoomDto.builder()
                .id(5L)
                .hotelId(1L)
                .roomNumber("101")
                .capacity(2)
                .pricePerNight(BigDecimal.TEN)
                .build();
        when(roomRepository.existsById(5L)).thenReturn(true);
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(mock()));
        when(roomRepository.existsByHotelIdAndRoomNumberAndIdNot(1L, "101", 5L)).thenReturn(false);
        Result result = strategy.isValidateForUpdate(dto);
        assertThat(result).isInstanceOf(SuccessResultImpl.class);
    }

    @Test
    void shouldReturnErrorWhenRoomDtoIsNull_OnDelete() {
        Result result = strategy.isValidateForDelete(null);
        assertThat(result).isInstanceOf(ErrorResultImpl.class);
        assertThat(result.message()).contains("Room data is required.");
    }

    @Test
    void shouldReturnErrorWhenRoomIdIsNull_OnDelete() {
        RoomDto dto = RoomDto.builder().build();
        Result result = strategy.isValidateForDelete(dto);
        assertThat(result).isInstanceOf(ErrorResultImpl.class);
        assertThat(result.message()).contains("If you want to delete this room, you must set id field");
    }

    @Test
    void shouldReturnErrorWhenRoomDoesNotExist_OnDelete() {
        RoomDto dto = RoomDto.builder().id(5L).build();
        when(roomRepository.existsById(5L)).thenReturn(false);
        Result result = strategy.isValidateForDelete(dto);
        assertThat(result).isInstanceOf(ErrorResultImpl.class);
        assertThat(result.message()).contains("Room with this id does not exist.");
    }

    @Test
    void shouldReturnSuccess_OnDelete() {
        RoomDto dto = RoomDto.builder().id(5L).build();
        when(roomRepository.existsById(5L)).thenReturn(true);
        Result result = strategy.isValidateForDelete(dto);
        assertThat(result).isInstanceOf(SuccessResultImpl.class);
    }
}