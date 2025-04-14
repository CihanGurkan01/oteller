package com.oteller.hotelservice.result;

import java.io.Serializable;

public interface Result extends Serializable {

    boolean success();

    String message();
    
    void setMessage(String message);

}
