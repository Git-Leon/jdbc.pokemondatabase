package com.zipcodewilmington.jdbc.tools.database.connection;

import com.zipcodewilmington.jdbc.tools.general.exception.SQLeonError;
import com.zipcodewilmington.jdbc.tools.general.functional.*;
import com.zipcodewilmington.jdbc.tools.general.logging.LoggerHandler;
import com.zipcodewilmington.jdbc.tools.general.logging.LoggerWarehouse;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by leon on 3/13/18.
 */
public class StatementExecutor implements Closeable {
    private final LoggerHandler logger;
    private final Connection connection;
    private final List<ResultSetHandler> resultSetHandlers = new ArrayList<>();

    public StatementExecutor(Connection connection) {
        String loggerName = getClass().getSimpleName() + connection.toString();
        this.logger = LoggerWarehouse.getLogger(loggerName);
        this.connection = connection;
    }

    /**
     * executes update statement on the respective connection object
     *
     * @param sql  string representative of a SQL update statement
     * @param args optional string formatting arguments
     */
    public void executeUpdate(String sql, Object... args) {
        String sqlStatement = String.format(sql, args);
        String error = "Failed to execute update `%s`.";
        String errorMessage = String.format(error, sqlStatement);
        ExceptionalConsumer<String> method = getScrollableStatement()::executeUpdate;
        ExceptionalConsumer.tryInvoke(method, sqlStatement, errorMessage);
    }

    /**
     * executes query statement on the respective connection object
     *
     * @param sql  string representative of a SQL query statement
     * @param args optional string formatting arguments
     */
    public ResultSetHandler executeQuery(String sql, Object... args) {
        ResultSetHandler resultSetHandler = this.query(sql, args);
        resultSetHandlers.add(resultSetHandler);
        return resultSetHandler;
    }

    /**
     * executes query statement on the respective connection object
     *
     * @param sql  string representative of a SQL query statement
     * @param args optional string formatting arguments
     * @return wrapper of ResultSet
     */
    private ResultSetHandler query(String sql, Object... args) {
        String sqlStatement = String.format(sql, args);
        String error = "Failed to execute query `%s`.";
        String errorMessage = String.format(error, sqlStatement);
        ExceptionalFunction<String, ResultSet> method = getScrollableStatement()::executeQuery;
        ResultSet rs = ExceptionalFunction.tryInvoke(method, sqlStatement, errorMessage);
        return new ResultSetHandler(rs);
    }


    public Connection getConnection() {
        return connection;
    }


    public void executeAndCommit(String s) {
        execute(s);
        commit();
    }

    /**
     * executes a statement on the respective connection object
     *
     * @param sql  string representative of a SQL update statement
     * @param args optional string formatting arguments
     */
    public void execute(String sql, Object... args) {
        String sqlStatement = String.format(sql, args);
        String errorString = "Failed to execute statement `%s`";
        String errorMessage = String.format(errorString, sql);
        ExceptionalConsumer<String> method = getScrollableStatement()::execute;
        ExceptionalConsumer.tryInvoke(method, sqlStatement, errorMessage);
        logger.info("Executed statement `%s`", sqlStatement);
    }

    /**
     * @return scroll-insensitive statement
     */
    public Statement getScrollableStatement() {
        String errorMessage = "Failed to create a Statement.";
        return ExceptionalBiFunction.tryInvoke(
                connection::createStatement,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY,
                errorMessage);
    }

    public void commit() {
        String errorMessage = "Failed to execute commit.";
        ExceptionalRunnable.tryInvoke(connection::commit, errorMessage);
    }

    public LoggerHandler getLogger() {
        return logger;
    }

    /**
     * closes connection object and all resultSetHandlers
     */
    @Override
    public void close() {
        String errorMessage = "Failed to close connection.";
        ExceptionalRunnable.tryInvoke(connection::close, errorMessage);
        resultSetHandlers.parallelStream().forEach(resultSetHandler -> resultSetHandler.close());
    }

    @Override // Invoked upon garbage collection
    public void finalize() {
        close();
    }
}
