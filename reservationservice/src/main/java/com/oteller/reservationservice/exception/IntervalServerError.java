package com.oteller.reservationservice.exception;

import com.oteller.reservationservice.controller.abstracts.AbstractActionResult;
import org.springframework.http.HttpStatus;

public class IntervalServerError extends AbstractActionResult {

    public IntervalServerError() {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
    }

	public IntervalServerError(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR,message);
	}

}
