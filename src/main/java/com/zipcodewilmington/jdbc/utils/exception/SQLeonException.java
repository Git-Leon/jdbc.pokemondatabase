package com.zipcodewilmington.jdbc.utils.exception;

public class SQLeonException extends Error {
    private final Throwable throwable;
    private final String errorMessage;

    public SQLeonException(Throwable throwable, String errorMessage) {
        super(throwable);
        this.throwable = throwable;
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