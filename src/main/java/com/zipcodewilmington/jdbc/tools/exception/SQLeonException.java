package com.zipcodewilmington.jdbc.tools.exception;

public class SQLeonException extends Error {
    private final String errorMessage;

    public SQLeonException(Throwable throwable, String errorMessage) {
        super(throwable);
        this.errorMessage = errorMessage + "\n\n" + throwable.getMessage();
    }

    public SQLeonException(Throwable throwable) {
        this(throwable, "");
    }

    @Override
    public void printStackTrace() {
        System.err.println(errorMessage);
    }
}