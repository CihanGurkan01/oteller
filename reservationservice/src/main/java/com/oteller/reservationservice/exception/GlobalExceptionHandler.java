package com.oteller.reservationservice.exception;

import com.oteller.reservationservice.controller.ActionResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:11.04.2025
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ActionResult internalServerError(Exception ex) {
        //log(ex);
        return new IntervalServerError(ex.getLocalizedMessage());
    }
}
