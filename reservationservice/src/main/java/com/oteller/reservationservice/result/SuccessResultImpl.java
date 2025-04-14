package com.oteller.reservationservice.result;

public class SuccessResultImpl extends ResultImpl {

    public SuccessResultImpl(String message) {
        super(true, message);
    }

    public SuccessResultImpl() {
        super(true);
    }

}
