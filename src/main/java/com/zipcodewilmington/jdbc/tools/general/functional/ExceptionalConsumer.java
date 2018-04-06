package com.zipcodewilmington.jdbc.tools.general.functional;

import com.zipcodewilmington.jdbc.tools.general.exception.SQLeonError;

/**
 * @author leon on 4/4/18.
 */
@FunctionalInterface
public interface ExceptionalConsumer<InputType> {
    void accept(InputType t) throws Throwable;


    static <ArgType> void tryInvoke(ExceptionalConsumer<ArgType> method, ArgType arg, String errorMessage) {
        try {
            method.accept(arg);
        } catch (Throwable throwable) {
            throw new SQLeonError(throwable, errorMessage);
        }
    }
}
