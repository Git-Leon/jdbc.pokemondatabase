package com.zipcodewilmington.jdbc.tools.database.connection;

import com.zipcodewilmington.jdbc.tools.general.exception.SQLeonError;
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
        try {
            getScrollableStatement().executeUpdate(sqlStatement);
        } catch (SQLException e) {
            String error = "Failed to execute update `%s`.";
            String errorMessage = String.format(error, sqlStatement);
            throw new SQLeonError(e, errorMessage);
        }
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
        ResultSet resultSet = null;
        String sqlStatement = String.format(sql, args);
        try {
            Statement statement = this.getScrollableStatement();
            resultSet = statement.executeQuery(sqlStatement);
        } catch (SQLException e) {
            String error = "Failed to execute query `%s`.";
            String errorMessage = String.format(error, sqlStatement);
            throw new SQLeonError(e, errorMessage);
        }
        return new ResultSetHandler(resultSet);
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
        try {
            String sqlStatement = String.format(sql, args);
            Statement statement = this.getScrollableStatement();
            statement.execute(sqlStatement);
            logger.info("Executed statement `%s`", sqlStatement);
        } catch (SQLException e) {
            String errorString = "Failed to execute statement `%s`";
            String errorMessage = String.format(errorString, sql);
            throw new SQLeonError(e, errorMessage);
        }
    }

    /**
     * @return scroll-insensitive statement
     */
    public Statement getScrollableStatement() {
        try {
            return connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            throw new SQLeonError(e, "Failed to create a Statement.");
        }
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new SQLeonError(e, "Failed to execute commit.");
        }
    }

    public LoggerHandler getLogger() {
        return logger;
    }

    /**
     * closes connection object and all resultSetHandlers
     */
    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new SQLeonError(e);
        }
        resultSetHandlers.parallelStream().forEach(resultSetHandler -> resultSetHandler.close());
    }

    public <InputType> void tryInvoke(Consumer<InputType> method, InputType arg) {
    }

    @Override // Invoked upon garbage collection
    public void finalize() {
        close();
    }
}
