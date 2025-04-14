package com.oteller.reservationservice.controller.abstracts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oteller.reservationservice.controller.ActionResult;
import jakarta.servlet.http.HttpServletResponse;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:11.04.2025
 */
@ToString
//@Getter
public abstract class AbstractActionResult implements ActionResult {

    private String message;

    private Object data;

    @JsonIgnore
    private HttpStatus httpStatus;

    public AbstractActionResult(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        setStatus(httpStatus);
    }

    public AbstractActionResult(HttpStatus httpStatus, String message, Object data) {
        this(httpStatus);
        this.message = message;
        this.data = data;
    }

    public AbstractActionResult(HttpStatus httpStatus, Object data) {
        this(httpStatus);
        this.data = data;
    }

    public AbstractActionResult(HttpStatus httpStatus, String message) {
        this(httpStatus);
        this.message = message;
    }

    public String getMessage() {
        return !Objects.isNull(message) ? message : getHttpStatus().getReasonPhrase();
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public Object getData() {
        return data;
    }

    public void setStatus(HttpStatus httpStatus) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder
                        .getRequestAttributes()).getResponse();
        response.setStatus(httpStatus.value());
    }

}
