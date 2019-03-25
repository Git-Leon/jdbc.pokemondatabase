package com.zipcodewilmington.jdbc.tools.executor;

import com.zipcodewilmington.jdbc.tools.resultset.ResultSetHandler;
import gitleon.utils.exceptionalfunctionalinterface.ExceptionalBiFunction;
import gitleon.utils.exceptionalfunctionalinterface.ExceptionalConsumer;
import gitleon.utils.exceptionalfunctionalinterface.ExceptionalFunction;
import gitleon.utils.exceptionalfunctionalinterface.ExceptionalRunnable;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public interface StatementExecutorInterface extends Closeable {

    /**
     * executes update statement on the respective connection object
     *
     * @param sql  string representative of a SQL update statement
     * @param args optional string formatting arguments
     */
    
    default void executeUpdate(String sql, Object... args) {
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
     * @return wrapper of ResultSet
     */
    default ResultSetHandler executeQuery(String sql, Object... args) {
        String error = "Failed to execute query `%s`.";
        String sqlStatement = String.format(sql, args);
        String errorMessage = String.format(error, sqlStatement);
        ExceptionalFunction<String, ResultSet> method = getScrollableStatement()::executeQuery;
        ResultSet rs = ExceptionalFunction.tryInvoke(method, sqlStatement, errorMessage);
        ResultSetHandler rsh = new ResultSetHandler(rs);
        getResultSetHandlers().add(rsh);
        return rsh;
    }


    
    default void executeAndCommit(String s) {
        execute(s);
        commit();
    }

    /**
     * executes a statement on the respective connection object
     *
     * @param sql  string representative of a SQL update statement
     * @param args optional string formatting arguments
     */
    
    default void execute(String sql, Object... args) {
        String error = "Failed to execute statement `%s`";
        String sqlStatement = String.format(sql, args);
        String errorMessage = String.format(error, sql);
        ExceptionalConsumer<String> method = getScrollableStatement()::execute;
        ExceptionalConsumer.tryInvoke(method, sqlStatement, errorMessage);
    }

    
    default void commit() {
        String errorMessage = "Failed to execute commit.";
        ExceptionalRunnable.tryInvoke(getConnection()::commit, errorMessage);
    }

    default Statement getScrollableStatement() {
        String errorMessage = "Failed to create a Statement.";
        return ExceptionalBiFunction.tryInvoke(
                getConnection()::createStatement,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY,
                errorMessage);
    }

    /**
     * closes connection object and all resultSetHandlers
     */
    @Override
    default void close() {
        String errorMessage = "Failed to close connection.";
        ExceptionalRunnable.tryInvoke(getConnection()::close, errorMessage);
        getResultSetHandlers().parallelStream().forEach(resultSetHandler -> resultSetHandler.close());
    }

    Connection getConnection();

    List<ResultSetHandler> getResultSetHandlers();
}
