package com.oteller.reservationservice.result;

public interface DataResult<T> extends Result {

    T data();
}
