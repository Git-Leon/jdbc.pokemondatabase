package com.zipcodewilmington.jdbc.oop.utils;

import java.io.Closeable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by leon on 3/13/18.
 */
public class ResultSetHandler implements Closeable {
    private final ResultSet resultSet;

    public ResultSetHandler(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public Stack<Map<String, String>> toStack() {
        Stack<Map<String, String>> stack = new Stack();
        stack.addAll(toList());
        return stack;
    }

    public List<Map<String, String>> toList() {
        List<Map<String,String>> list = null;
        try {
            list = _toList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private List<Map<String, String>> _toList() throws SQLException {
        ResultSetMetaData md = resultSet.getMetaData();
        int columns = md.getColumnCount();
        List<Map<String, String>> list = new ArrayList<>();

        while (resultSet.next()) {
            HashMap<String, String> row = new HashMap<>(columns);
            for (int i = 1; i <= columns; ++i) {
                String columnName = md.getColumnName(i);
                Object columnValue = resultSet.getObject(i);
                row.put(columnName, String.valueOf(columnValue));
            }
            list.add(row);
        }
        return list;
    }

    @Override
    public void close() {
        try {
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void finalize() {
        close();
    }

    public String getColumnName(int i) {
        String columnName = "";
        try {
            ResultSetMetaData rsmd = resultSet.getMetaData();
            columnName = rsmd.getColumnName(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columnName;
    }
}
