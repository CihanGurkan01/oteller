package com.oteller.hotelservice.exception;

import com.oteller.hotelservice.controller.abstracts.AbstractActionResult;
import org.springframework.http.HttpStatus;

public class IntervalServerError extends AbstractActionResult {

    public IntervalServerError() {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
    }

	public IntervalServerError(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR,message);
	}

}
