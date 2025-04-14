package com.oteller.reservationservice.result;

public class SuccessfulDataResultImpl<T> extends DataResultImpl<T> {

    public SuccessfulDataResultImpl() {
        super(null, true);
    }

    public SuccessfulDataResultImpl(String message) {
        super(null, true, message);
    }

    public SuccessfulDataResultImpl(T data, String message) {
        super(data, true, message);
    }

    public SuccessfulDataResultImpl(T data) {
        super(data, true);
    }
}