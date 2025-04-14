package com.oteller.hotelservice.service.validation;

import com.oteller.hotelservice.data.repository.HotelRepository;
import com.oteller.hotelservice.result.Result;
import com.oteller.hotelservice.result.SuccessResultImpl;
import com.oteller.hotelservice.result.ErrorResultImpl;
import com.oteller.hotelservice.service.model.AddressDto;
import com.oteller.hotelservice.service.model.HotelDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class HotelValidationStrategyTest {

    private HotelValidationStrategy strategy;
    private HotelRepository hotelRepository;

    @BeforeEach
    void setup() {
        hotelRepository = mock(HotelRepository.class);
        strategy = new HotelValidationStrategy(hotelRepository);
    }

    // ----- Create Validations -----

    @Test
    void shouldReturnErrorWhenHotelNameIsNull_OnCreate() {
        HotelDto dto = HotelDto.builder().build();
        dto.setAddress(new AddressDto());

        Result result = strategy.isValidateForCreate(dto);

        assertThat(result).isInstanceOf(ErrorResultImpl.class);
        assertThat(result.message()).contains("set name");
    }

    @Test
    void shouldReturnErrorWhenAddressIsNull_OnCreate() {
        HotelDto dto = HotelDto.builder().build();
        dto.setName("Test Hotel");

        Result result = strategy.isValidateForCreate(dto);

        assertThat(result).isInstanceOf(ErrorResultImpl.class);
        assertThat(result.message()).contains("set address");
    }

    @Test
    void shouldReturnErrorWhenStarRatingInvalid_OnCreate() {
        HotelDto dto = HotelDto.builder().build();
        dto.setName("Test Hotel");
        dto.setAddress(new AddressDto());
        dto.setStarRating(6); // geçersiz

        Result result = strategy.isValidateForCreate(dto);

        assertThat(result).isInstanceOf(ErrorResultImpl.class);
        assertThat(result.message()).contains("between 1 and 5");
    }

    @Test
    void shouldReturnErrorWhenHotelAlreadyExists_OnCreate() {
        HotelDto dto = HotelDto.builder().build();
        dto.setId(1L);
        dto.setName("Test Hotel");
        AddressDto address = new AddressDto();
        address.setAddressStreet("Street");
        address.setAddressCity("City");
        address.setAddressCountry("Country");
        dto.setAddress(address);
        dto.setStarRating(3);

        when(hotelRepository.existsHotelWithSameAddress("Test Hotel", "Street", "City", "Country", 1L))
                .thenReturn(true);

        Result result = strategy.isValidateForCreate(dto);

        assertThat(result).isInstanceOf(ErrorResultImpl.class);
        assertThat(result.message()).contains("already exists");
    }

    @Test
    void shouldReturnSuccess_OnValidCreate() {
        HotelDto dto = HotelDto.builder().build();
        dto.setId(2L);
        dto.setName("New Hotel");
        AddressDto address = new AddressDto();
        address.setAddressStreet("Street");
        address.setAddressCity("City");
        address.setAddressCountry("Country");
        dto.setAddress(address);
        dto.setStarRating(4);

        when(hotelRepository.existsHotelWithSameAddress(any(), any(), any(), any(), any()))
                .thenReturn(false);

        Result result = strategy.isValidateForCreate(dto);

        assertThat(result).isInstanceOf(SuccessResultImpl.class);
    }

    // ----- Delete Validations -----

    @Test
    void shouldReturnErrorWhenIdIsNull_OnDelete() {
        HotelDto dto = HotelDto.builder().build(); // id null

        Result result = strategy.isValidateForDelete(dto);

        assertThat(result).isInstanceOf(ErrorResultImpl.class);
        assertThat(result.message()).contains("set id");
    }

    @Test
    void shouldReturnErrorIfHotelDoesNotExist_OnDelete() {
        HotelDto dto = HotelDto.builder().build();
        dto.setId(123L);

        when(hotelRepository.existsById(123L)).thenReturn(false);

        Result result = strategy.isValidateForDelete(dto);

        assertThat(result).isInstanceOf(ErrorResultImpl.class);
        assertThat(result.message()).contains("does not exist");
    }

    @Test
    void shouldReturnSuccess_OnValidDelete() {
        HotelDto dto = HotelDto.builder().build();
        dto.setId(123L);

        when(hotelRepository.existsById(123L)).thenReturn(true);

        Result result = strategy.isValidateForDelete(dto);

        assertThat(result).isInstanceOf(SuccessResultImpl.class);
    }

    // ----- Update Validations (delegates to delete) -----

    @Test
    void shouldReturnError_OnInvalidUpdate() {
        HotelDto dto = HotelDto.builder().build(); // id null → geçersiz

        Result result = strategy.isValidateForUpdate(dto);

        assertThat(result).isInstanceOf(ErrorResultImpl.class);
    }

    @Test
    void shouldReturnSuccess_OnValidUpdate() {
        HotelDto dto = HotelDto.builder().build();
        dto.setId(1L);
        when(hotelRepository.existsById(1L)).thenReturn(true);

        Result result = strategy.isValidateForUpdate(dto);

        assertThat(result).isInstanceOf(SuccessResultImpl.class);
    }
}
