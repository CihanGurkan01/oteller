package com.oteller.reservationservice.result;

public class ErrorResultImpl extends ResultImpl {

    public ErrorResultImpl(String message) {
        super(false, message);
    }

    public ErrorResultImpl() {
        super(false);
    }

}
