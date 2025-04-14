package com.oteller.hotelservice.service;


import com.oteller.hotelservice.result.DataResult;
import com.oteller.hotelservice.result.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudService<T,R> {
    Result create(T object);

    Result update(T object);

    Result deletePermanently(T object);

    DataResult<Page<R>> findAll(Pageable pageable);
}
