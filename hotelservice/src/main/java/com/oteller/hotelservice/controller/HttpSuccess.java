package com.oteller.hotelservice.controller;

import com.oteller.hotelservice.controller.abstracts.AbstractActionResult;
import org.springframework.http.HttpStatus;

/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:11.04.2025
 */
public class HttpSuccess extends AbstractActionResult {

    public HttpSuccess() {
        super(HttpStatus.OK);
    }

    public HttpSuccess(String message) {
        super(HttpStatus.OK, message);
    }

    public HttpSuccess(Object data) {
        super(HttpStatus.OK, data);
    }

    public HttpSuccess(String message, Object data) {
        super(HttpStatus.OK, message, data);
    }

}
