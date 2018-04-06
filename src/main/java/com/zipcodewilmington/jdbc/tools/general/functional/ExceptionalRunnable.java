package com.zipcodewilmington.jdbc.tools.general.functional;

import com.zipcodewilmington.jdbc.tools.general.exception.SQLeonError;

/**
 * @author leon on 4/5/18.
 */

@FunctionalInterface
public interface ExceptionalRunnable {
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see     java.lang.Thread#run()
     */
    void run() throws Throwable;

    static <T> void tryInvoke(ExceptionalRunnable method, String errorMessage) {
        try {
            method.run();
        } catch (Throwable throwable) {
            throw new SQLeonError(throwable, errorMessage);
        }
    }
}
