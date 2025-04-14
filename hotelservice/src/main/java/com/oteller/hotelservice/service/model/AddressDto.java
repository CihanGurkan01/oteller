package com.oteller.hotelservice.service.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:12.04.2025
 * Time:13:09
 */
@Data
public class AddressDto {
    private String addressStreet;
    private String addressCity;
    private String addressCountry;
    private String addressFullAddress;

}
