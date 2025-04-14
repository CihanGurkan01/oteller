package com.oteller.hotelservice.controller;

import com.oteller.hotelservice.controller.abstracts.AbstractActionResult;
import org.springframework.http.HttpStatus;

/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:11.04.2025
 */
public class BadRequest extends AbstractActionResult {

    public BadRequest() {
        super(HttpStatus.BAD_REQUEST);
    }

    public BadRequest(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public BadRequest(Object data) {
        super(HttpStatus.BAD_REQUEST, data);
    }

}
