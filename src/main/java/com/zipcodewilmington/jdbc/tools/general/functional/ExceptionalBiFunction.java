package com.zipcodewilmington.jdbc.tools.general.functional;

import com.zipcodewilmington.jdbc.tools.general.exception.SQLeonError;

import java.util.function.BiFunction;

/**
 * @author leon on 4/6/18.
 */
public interface ExceptionalBiFunction<Arg1Type, Arg2Type, ReturnType> {
    /**
     * Applies this function to the given arguments.
     *
     * @param arg1 the first function argument
     * @param arg2 the second function argument
     * @return the function result
     */
    ReturnType apply(Arg1Type arg1, Arg2Type arg2) throws Throwable;


    static <Arg1Type, Arg2Type, ReturnType> ReturnType tryInvoke(ExceptionalBiFunction<Arg1Type, Arg2Type, ReturnType> method, Arg1Type arg1Value, Arg2Type arg2Value, String errorMessage) {
        try {
            return method.apply(arg1Value, arg2Value);
        } catch (Throwable t) {
            throw new SQLeonError(t, errorMessage);
        }
    }
}
