package com.oteller.hotelservice.data.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:12.04.2025
 * Time:13:09
 */
@Data
@Embeddable
public class AddressEntity {
    private String addressStreet;
    private String addressCity;
    private String addressCountry;
    private String addressFullAddress;

}
