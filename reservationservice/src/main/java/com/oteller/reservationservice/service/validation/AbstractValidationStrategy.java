package com.oteller.reservationservice.service.validation;


import com.oteller.reservationservice.result.ErrorResultImpl;
import com.oteller.reservationservice.result.Result;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:12.04.2025
 * Time:13:03
 */
public abstract class AbstractValidationStrategy<T> {

    abstract Result isValidateForCreate(T t);

    abstract Result isValidateForUpdate(T t);

    abstract Result isValidateForDelete(T t);



    public Result checkNull(Object obj, String message) {
        return obj == null ? new ErrorResultImpl(message) : null;
    }

    public boolean isNullOrEmpty(Object obj) {
        if (obj == null) return true;
        if (obj instanceof String str) return str.isBlank();
        if (obj instanceof Collection<?> col) return col.isEmpty();
        if (obj instanceof Map<?, ?> map) return map.isEmpty();
        if (obj instanceof Optional<?> opt) return opt.isEmpty();

        return false;
    }

    public  boolean isNotNullOrEmpty(Object obj) {
        return !isNullOrEmpty(obj);
    }
}
