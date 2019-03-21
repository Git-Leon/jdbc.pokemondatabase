package com.zipcodewilmington.jdbc.tools.exception;

public class SQLError extends Error {
    private final String errorMessage;

    public SQLError(Throwable throwable, String errorMessage) {
        super(throwable);
        this.errorMessage = errorMessage + "\n\n" + throwable.getMessage();
    }

    public SQLError(Throwable throwable) {
        this(throwable, "");
    }

    @Override
    public void printStackTrace() {
        System.err.println(errorMessage);
    }
}