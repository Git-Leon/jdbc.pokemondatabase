package com.zipcodewilmington.jdbc.utils.database.connection;

import com.zipcodewilmington.jdbc.utils.database.resultset.ResultSetHandler;
import com.zipcodewilmington.jdbc.utils.exception.SQLeonException;
import com.zipcodewilmington.jdbc.utils.logging.LoggerHandler;
import com.zipcodewilmington.jdbc.utils.logging.LoggerWarehouse;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StatementExecutor implements Closeable {
    private final LoggerHandler logger;
    private final List<ResultSetHandler> resultSetHandlers = new ArrayList<>();
    private final Connection connection;

    public StatementExecutor(Connection connection) {
        String loggerName = getClass().getSimpleName() + connection.toString();
        this.logger = LoggerWarehouse.getLogger(loggerName);
        this.connection = connection;
    }

    /**
     * executes update statement on the respective connection object
     * @param updateStatement string representative of a SQL update statement
     */
    public void executeUpdate(String updateStatement) {
        try {
            getScrollableStatement().executeUpdate(updateStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * executes query statement on the respective connection object
     * @param queryStatement string representative of a SQL query statement
     */
    public ResultSetHandler executeQuery(String queryStatement) {
        ResultSetHandler resultSetHandler = this.query(queryStatement);
        resultSetHandlers.add(resultSetHandler);
        return resultSetHandler;
    }

    /**
     * executes query statement on the respective connection object
     * @param queryStatement string representative of a SQL query statement
     * @return wrapper of ResultSet
     */
    private ResultSetHandler query(String queryStatement) {
        ResultSet resultSet = null;
        try {
            Statement statement = this.getScrollableStatement();
            resultSet = statement.executeQuery(queryStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResultSetHandler(resultSet);
    }


    public Connection getConnection() {
        return connection;
    }


    /**
     * closes connection object and all resultSetHandlers
     */
    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (ResultSetHandler resultSetHandler : resultSetHandlers) {
            resultSetHandler.close();
        }
    }

    public void executeAndCommit(String s) {
        execute(s);
        commit();
    }

    public void execute(String sql, Object... args) {
        try {
            String sqlStatement = String.format(sql, args);
            Statement statement = this.getScrollableStatement();
            statement.execute(sqlStatement);
            logger.info("Executed statement `%s`", sqlStatement);
        } catch (SQLException e) {
            String errorString = "Failed to execute statement `%s`";
            String errorMessage = String.format(errorString, sql);
            throw new SQLeonException(e, errorMessage);
        }
    }

    public Statement getScrollableStatement() {
        try {
            return connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            throw new SQLeonException(e, "Failed to create a Statement.");
        }
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new SQLeonException(e, "Failed to execute commit.");
        }
    }

    public LoggerHandler getLogger() {
        return logger;
    }

    @Override // Invoked upon garbage collection
    public void finalize() {
        close();
    }
}
