package com.oteller.reservationservice.controller.abstracts;


import com.oteller.reservationservice.controller.ActionResult;
import com.oteller.reservationservice.controller.BadRequest;
import com.oteller.reservationservice.controller.CustomController;
import com.oteller.reservationservice.controller.HttpSuccess;

/**
 * Author: Cihan GURKAN
 * User:cihan
 * Date:11.04.2025
 */
public abstract class AbstractController implements CustomController {

    @Override
    public ActionResult ok(String message) {
        return new HttpSuccess(message);
    }

    @Override
    public ActionResult ok(Object data) {
        return new HttpSuccess(data);
    }

    @Override
    public ActionResult ok(String message, Object data) {
        return new HttpSuccess(message, data);
    }

    @Override
    public ActionResult badRequest() {
        return new BadRequest(null);
    }

    @Override
    public ActionResult badRequest(String message) {
        return new BadRequest(message);
    }

}