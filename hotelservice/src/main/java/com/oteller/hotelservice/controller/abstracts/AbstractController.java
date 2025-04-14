package com.oteller.hotelservice.controller.abstracts;

import com.oteller.hotelservice.controller.ActionResult;
import com.oteller.hotelservice.controller.BadRequest;
import com.oteller.hotelservice.controller.CustomController;
import com.oteller.hotelservice.controller.HttpSuccess;

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