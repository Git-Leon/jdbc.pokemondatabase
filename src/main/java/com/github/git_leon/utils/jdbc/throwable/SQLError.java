package com.github.git_leon.utils.jdbc.throwable;

/**
 * Created by leon on 3/13/18.
 */
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