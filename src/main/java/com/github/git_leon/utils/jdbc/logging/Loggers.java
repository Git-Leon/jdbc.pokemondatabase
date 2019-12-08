package com.github.git_leon.utils.jdbc.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by leon on 8/17/17.
 */
public class Loggers {
    public static void disableLogging(Class<?>... classes) {
        for (Class<?> cls : classes) {
            Logger.getLogger(cls.getName()).setLevel(Level.OFF);
        }
    }
}
