package com.example.aplikasikursus.result;

public class Result<T> {
    private T object;
    private boolean isError;
    private String message;

    public Result(T object, boolean isError, String message) {
        this.object = object;
        this.isError = isError;
        this.message = message;
    }

    public T getObject() {
        return object;
    }

    public boolean isError() {
        return isError;
    }

    public String getMessage() {
        return message;
    }
}
