package com.zipcodewilmington.jdbc.tools.general.functional;

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
}