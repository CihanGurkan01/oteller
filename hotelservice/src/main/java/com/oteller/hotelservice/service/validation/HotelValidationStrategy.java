package com.oteller.hotelservice.service.validation;

import com.oteller.hotelservice.data.repository.HotelRepository;
import com.oteller.hotelservice.result.ErrorResultImpl;
import com.oteller.hotelservice.result.Result;
import com.oteller.hotelservice.result.SuccessResultImpl;
import com.oteller.hotelservice.service.model.AddressDto;
import com.oteller.hotelservice.service.model.HotelDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:12.04.2025
 */
@Component
@RequiredArgsConstructor
public class HotelValidationStrategy extends AbstractValidationStrategy<HotelDto> {
    private final HotelRepository hotelRepository;

    @Override
    public Result isValidateForUpdate(HotelDto hotelDto){
        Result result = isValidateForDelete(hotelDto);
        if(!result.success()){
            return result;
        }

        return new SuccessResultImpl();
    }

    @Override
    public Result isValidateForDelete(HotelDto hotelDto){

        Result nullCheck = checkNull(hotelDto, "Hotel data is required.");
        if (nullCheck != null) return nullCheck;

        if (isNullOrEmpty(hotelDto.getId())) {
            return new ErrorResultImpl("If you want to delete this hotel, you must set id field");
        }

        if(!hotelRepository.existsById(hotelDto.getId())){
            return new ErrorResultImpl("Hotel with this id does not exist.");
        }

        return new SuccessResultImpl();

    }

    @Override
    public Result isValidateForCreate(HotelDto hotelDto){

        Result result = checkFields(hotelDto);
        if (!result.success()){
            return result;
        }

        AddressDto address = hotelDto.getAddress();
        if (isAddressValid(address) &&
                hotelRepository.existsHotelWithSameAddress(
                        hotelDto.getName(),
                        address.getAddressStreet(),
                        address.getAddressCity(),
                        address.getAddressCountry(),
                        hotelDto.getId())) {
            return new ErrorResultImpl("Hotel with these name, addressStreet, addressCity and addressCountry already exists.");
        }

        return new SuccessResultImpl();
    }

    private Result checkFields(HotelDto hotelDto) {
        Result nullCheck = checkNull(hotelDto, "Hotel data is required.");
        if (nullCheck != null) return nullCheck;

        if (isNullOrEmpty(hotelDto.getName())) {
            return new ErrorResultImpl("If you want to add hotel, you must set name field");
        }

        if (isNullOrEmpty(hotelDto.getAddress())) {
            return new ErrorResultImpl("If you want to add hotel, you must set address field");
        }

        Integer starRating = hotelDto.getStarRating();
        if (isNotNullOrEmpty(starRating) && (starRating < 1 || starRating > 5)) {
            return new ErrorResultImpl("Star rating must be between 1 and 5.");
        }
        return new SuccessResultImpl();
    }

    private boolean isAddressValid(AddressDto address) {
        return isNotNullOrEmpty(address) && isNotNullOrEmpty(address.getAddressStreet())
                && isNotNullOrEmpty(address.getAddressCity()) && isNotNullOrEmpty(address.getAddressCountry());
    }


}
