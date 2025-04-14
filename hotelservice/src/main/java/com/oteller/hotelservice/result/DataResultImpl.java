package com.oteller.hotelservice.result;

public class DataResultImpl<T> implements DataResult<T> {

    private T data;
    private boolean success;
    private String message;

    public DataResultImpl(T data, boolean success, String message) {
        this.data = data;
        this.message = message;
        this.success = success;
    }

    public DataResultImpl(T data, boolean success) {
        this.data = data;
        this.success = success;
    }

    @Override
    public T data() {
        return data;
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
		this.message = message;
		
	}


}
