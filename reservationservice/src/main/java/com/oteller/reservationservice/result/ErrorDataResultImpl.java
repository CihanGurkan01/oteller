package com.oteller.reservationservice.result;

public class ErrorDataResultImpl<T> extends DataResultImpl<T> {

    public ErrorDataResultImpl() {
        super(null, false);
    }

    public ErrorDataResultImpl(String message) {
        super(null, false, message);
    }

    public ErrorDataResultImpl(T data, String message) {
        super(data, false, message);
    }

    public ErrorDataResultImpl(T data) {
        super(data, false);
    }
}
