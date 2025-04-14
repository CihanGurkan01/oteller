package com.oteller.reservationservice.controller;

public interface CustomController {

    ActionResult ok(String message);

    ActionResult ok(Object data);

    ActionResult ok(String message, Object data);

    ActionResult badRequest();

    ActionResult badRequest(String message);

}
