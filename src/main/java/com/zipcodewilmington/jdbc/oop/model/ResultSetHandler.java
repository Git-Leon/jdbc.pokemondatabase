package com.zipcodewilmington.jdbc.oop.model;

import java.io.Closeable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by leon on 3/13/18.
 */
public class ResultSetHandler implements Closeable {
    private final ResultSet resultSet;

    public ResultSetHandler(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public void close() {
        try {
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <ColumnType> Map<String, ColumnType> getColumn(String columnName) {
        return null;
    }
}
