package com.oteller.reservationservice.enumeration;

import lombok.Getter;

@Getter
public enum StatusEnumeration {
    ACTIVE("Active","A"),
    PASSIVE("Passive","P");

    private final String name;
    private final String code;

    StatusEnumeration(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
