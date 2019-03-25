package com.zipcodewilmington.jdbc.tools.executor;

import com.zipcodewilmington.jdbc.tools.connection.ConnectionWrapper;
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
        new ConnectionWrapper(connection).setAutoCommit(false);
    }

    /**
     * executes query statement on the respective connection object
     *
     * @param sql  string representative of a SQL query statement
     * @param args optional string formatting arguments
     * @return wrapper of ResultSet
     */
    @Override
    public ResultSetHandler executeQuery(String sql, Object... args) {
        String sqlStatement = String.format(sql, args);
        ResultSetHandler rsh = StatementExecutorInterface.super.executeQuery(sqlStatement);
        getLogger().info("Executed query `%s`", sqlStatement);
        return rsh;
    }


    /**
     * executes a statement on the respective connection object
     *
     * @param sql  string representative of a SQL update statement
     * @param args optional string formatting arguments
     */
    @Override
    public void execute(String sql, Object... args) {
        String sqlStatement = String.format(sql, args);
        StatementExecutorInterface.super.execute(sqlStatement);
        getLogger().info("Executed statement `%s`", sqlStatement);
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

    public LoggerHandler getLogger() {
        return logger;
    }
}
