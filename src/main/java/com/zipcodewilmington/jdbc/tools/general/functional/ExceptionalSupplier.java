package com.zipcodewilmington.jdbc.tools.general.functional;

import com.zipcodewilmington.jdbc.tools.general.exception.SQLeonError;

/**
 * @author leon on 4/5/18.
 */
@FunctionalInterface
public interface ExceptionalSupplier<T> {
    /**
     * Gets a result.
     *
     * @return a result
     */
    T get() throws Throwable;

    static <ReturnType> ReturnType tryInvoke(ExceptionalSupplier<ReturnType> method, String errorMessage) {
        try {
            return method.get();
        } catch (Throwable throwable) {
            throw new SQLeonError(throwable, errorMessage);
        }
    }
}

