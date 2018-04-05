package com.zipcodewilmington.jdbc.tools.general.logging;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.*;

import static java.util.logging.Level.*;

/**
 * Created by leon on 5/15/17.
 */
public final class LoggerHandler {
    private final Logger logger;
    private final String loggerName;
    private boolean printingEnabled;

    public LoggerHandler(Class c) {
        this(c.getSimpleName());
    }

    public LoggerHandler(String loggerName) {
        this(Logger.getLogger(loggerName));
    }

    public LoggerHandler(Logger logger) {
        this.logger = logger;
        this.loggerName = logger.getName();
        logger.setUseParentHandlers(false);
        this.removeHandlers();
        this.logger.addHandler(getFileHandler());
        this.printingEnabled = true;
    }

    private void removeHandlers() {
        Logger globalLogger = Logger.getGlobal();
        Handler[] handlers = globalLogger.getHandlers();
        for(Handler handler : handlers) {
            globalLogger.removeHandler(handler);
        }
    }

    public void throwable(Throwable t, Level level) {
        StringWriter out = new StringWriter();
        t.printStackTrace(new PrintWriter(out));
        String description = out.toString();
        log(level, description);
    }

    public void throwable(Throwable t) {
        throwable(t, WARNING);
    }

    public void info(String s, Object... args) {
        log(INFO, s, args);
    }

    public void eval(boolean condition, String s, Object... args) {
        String prefixedMessage = "%s " + s;
        String prefix = condition ? "Successfully" : "Unsuccesfully";
        info(prefixedMessage, prefix, args);
    }

    public void warn(String s, Object... args) {
        log(WARNING, String.format(s, args));
    }

    public void error(String s, Object... args) {
        log(SEVERE, s, args);
    }

    private void log(Level level, String message, Object... messageArgs) {
        String info = String.format(message, messageArgs);
        if(printingEnabled) {
            System.out.println(info);
        }
        logger.log(level, info);
    }

    private FileHandler getFileHandler() {
        return getFileHandler(new SimpleFormatter());
    }

    private FileHandler getFileHandler(Formatter formatter) {
        FileHandler fh = null;
        try {
            String fileName = String.format("./target/%s-%s.leonlog", loggerName, System.nanoTime());
            fh = new FileHandler(fileName);
            fh.setFormatter(formatter);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (NullPointerException npe) {

        }
        return fh;
    }

    public void enablePrinting() {
        this.printingEnabled = true;
    }

    public void disablePrinting() {
        this.printingEnabled = false;
    }
}
