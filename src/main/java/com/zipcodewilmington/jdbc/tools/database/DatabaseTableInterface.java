package com.zipcodewilmington.jdbc.tools.database;

import com.zipcodewilmington.jdbc.tools.database.connection.ResultExtractor;
import com.zipcodewilmington.jdbc.tools.database.connection.ResultSetHandler;
import com.zipcodewilmington.jdbc.tools.database.connection.StatementExecutor;

public interface DatabaseTableInterface {
    ResultSetHandler all();

    ResultSetHandler select(String fieldNames);

    ResultSetHandler where(String fieldNames, String condition);

    ResultSetHandler limit(String fieldNames, Integer numberOfRows);

    ResultSetHandler limit(Integer numberOfRows);

    ResultSetHandler selectWhereLimit(String fieldNames, String condition, Integer numberOfRows);

    ResultSetHandler where(String condition);

    <T> T find(Long id, ResultExtractor<T> extractor);

    StatementExecutor getStatementExecutor();

    String getTableName();

    @Override
    String toString();
}
