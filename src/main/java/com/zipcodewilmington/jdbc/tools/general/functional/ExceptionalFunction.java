package com.zipcodewilmington.jdbc.tools.general.functional;

import com.zipcodewilmington.jdbc.tools.general.exception.SQLeonError;

/**
 * @author leon on 4/5/18.
 */

@FunctionalInterface
public interface ExceptionalFunction<T, R> {

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     */
    R apply(T t) throws Throwable;

    static <ArgType, ReturnType> ReturnType tryInvoke(ExceptionalFunction<ArgType, ReturnType> method, ArgType argValue, String errorMessage) {
        try {
            return method.apply(argValue);
        } catch (Throwable t) {
            throw new SQLeonError(t, errorMessage);
        }
    }
}