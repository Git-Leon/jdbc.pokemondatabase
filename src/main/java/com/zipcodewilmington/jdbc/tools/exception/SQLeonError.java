package com.zipcodewilmington.jdbc.tools.exception;

public class SQLeonError extends Error {
    private final String errorMessage;

    public SQLeonError(Throwable throwable, String errorMessage) {
        super(throwable);
        this.errorMessage = errorMessage + "\n\n" + throwable.getMessage();
    }

    public SQLeonError(Throwable throwable) {
        this(throwable, "");
    }

    @Override
    public void printStackTrace() {
        System.err.println(errorMessage);
    }
}