package com.zipcodewilmington.jdbc.tools.general.functional;

/**
 * @author leon on 4/4/18.
 */
@FunctionalInterface
public interface ExceptionalConsumer<T> {
    void accept(T t) throws Throwable;
}
