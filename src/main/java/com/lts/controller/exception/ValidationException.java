package com.lts.controller.exception;

public class ValidationException extends RuntimeException {

    private Object data;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(Object data, String message) {
        super(message);
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
