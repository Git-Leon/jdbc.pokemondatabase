package com.zipcodewilmington.jdbc.tools.executor;

import com.zipcodewilmington.jdbc.tools.resultset.ResultSetHandler;
import com.zipcodewilmington.jdbc.tools.logging.LoggerHandler;
import com.zipcodewilmington.jdbc.tools.logging.LoggerWarehouse;
import gitleon.utils.exceptionalfunctionalinterface.ExceptionalBiFunction;
import gitleon.utils.exceptionalfunctionalinterface.ExceptionalConsumer;
import gitleon.utils.exceptionalfunctionalinterface.ExceptionalFunction;
import gitleon.utils.exceptionalfunctionalinterface.ExceptionalRunnable;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leon on 3/13/18.
 */
public class StatementExecutor implements StatementExecutorInterface {
    private final LoggerHandler logger;
    private final Connection connection;
    private final List<ResultSetHandler> resultSetHandlers = new ArrayList<>();

    public StatementExecutor(Connection connection) {
        String loggerName = getClass().getSimpleName() + connection.toString();
        this.logger = LoggerWarehouse.getLogger(loggerName);
        this.connection = connection;
        try {
            this.connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override// Invoked upon garbage collection
    public void finalize() {
        close();
    }
    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public List<ResultSetHandler> getResultSetHandlers() {
        return resultSetHandlers;
    }

    @Override
    public LoggerHandler getLogger() {
        return logger;
    }
}
