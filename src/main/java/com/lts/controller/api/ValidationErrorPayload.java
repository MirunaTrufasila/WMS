package com.lts.controller.api;

public class ValidationErrorPayload<T> {

    private String message;
    private final T payload;

    public ValidationErrorPayload(T payload, String message) {
        this.payload = payload;
        this.message = message;
    }

    public T getPayload() {
        return payload;
    }

    public String getMessage() {
        return message;
    }
}
