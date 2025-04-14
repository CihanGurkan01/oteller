package com.oteller.hotelservice.result;

import lombok.ToString;

@ToString
public class ResultImpl implements Result {

    private boolean success;

    private String message;

    public ResultImpl(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ResultImpl(boolean success) {
        this.success = success;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public String message() {
        return message;
    }

	@Override
	public void setMessage(String message) {
		this.message = 	message;
	}

}
