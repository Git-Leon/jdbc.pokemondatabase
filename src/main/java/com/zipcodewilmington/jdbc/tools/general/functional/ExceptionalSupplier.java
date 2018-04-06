package com.zipcodewilmington.jdbc.tools.general.functional;

/**
 * @author leon on 4/5/18.
 */
@FunctionalInterface
public interface ExceptionalSupplier<T> {
    T get() throws Throwable;
}

