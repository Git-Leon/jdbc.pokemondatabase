package com.zipcodewilmington.jdbc.oop.utils.logging;

import com.zipcodewilmington.jdbc.oop.utils.StatementExecutor;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by leon on 5/15/17.
 */
public final class LoggerWarehouse {
    private static final LoggerHandler globalLogger = new LoggerHandler(Logger.getGlobal());
    private static final Map<String, LoggerHandler> loggerMap = new HashMap<>();

    /**
     * Only one logger per class
     * @param loggerName - name of logger
     * @return respective LoggerHandler object
     */
    public static final LoggerHandler getLogger(String loggerName) {
        addLogger(loggerName);
        return loggerMap.get(loggerName);
    }


    /**
     * Ensures each logger is only tied to a single class
     * @param loggerName - name of logger
     * */
    private static final void addLogger(String loggerName) {
        if (!loggerMap.containsKey(loggerName)) {
            globalLogger.info(String.format("Instantiating logger for [ %s ] ... ", loggerName));
            loggerMap.put(loggerName, new LoggerHandler(loggerName));
        }
    }

    public static LoggerHandler getLogger(Class<?> aClass) {
        return getLogger(aClass.getName());
    }
}
