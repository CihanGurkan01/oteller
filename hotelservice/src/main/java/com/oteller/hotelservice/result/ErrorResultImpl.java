package com.oteller.hotelservice.result;

public class ErrorResultImpl extends ResultImpl {

    public ErrorResultImpl(String message) {
        super(false, message);
    }

    public ErrorResultImpl() {
        super(false);
    }

}
