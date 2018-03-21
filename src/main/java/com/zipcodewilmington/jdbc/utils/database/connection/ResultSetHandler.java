package com.zipcodewilmington.jdbc.utils.database.connection;

import com.zipcodewilmington.jdbc.utils.exception.SQLeonException;

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
    private ResultSetMetaData metaData;

    public ResultSetHandler(ResultSet resultSet) {
        this.resultSet = resultSet;
        this.metaData = getMetaData();
    }

    public Stack<Map<String, String>> toStack() {
        Stack<Map<String, String>> stack = new Stack<Map<String, String>>() {
            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                for (Map<String, String> row : this) {
                    sb.append("\n" + row.toString());
                }
                return sb.toString();
            }
        };
        stack.addAll(toList());
        return stack;
    }

    // Returns a { columnName : columnValues } structured hash map.
    public Map<String, String[]> asColumnNameMap() {
        Map<String, String[]> map = new HashMap<>();
        for(String columnName : getColumnNames()) {
            map.put(columnName, getRows(columnName));
        }
        return map;
    }

    public List<Map<String, String>> toList() {
        List<Map<String, String>> list = null;
        try {
            list = _toList();
        } catch (SQLException e) {
            throw new SQLeonException(e, "Failed to create list from result set");
        }
        return list;
    }

    private List<Map<String, String>> _toList() throws SQLException {
        ResultSetMetaData md = getMetaData();
        int columns = md.getColumnCount();
        List<Map<String, String>> list = new ArrayList<>();

        resultSet.beforeFirst();
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
            columnName = getMetaData().getColumnName(i);
        } catch (SQLException e) {
            String errorString = "Failed to get column name of column number `%s`";
            String errorMessage = String.format(errorString, i);
            throw new SQLeonException(e, errorMessage);
        }
        return columnName;
    }

    public Integer getColumnCount() {
        try {
            return getMetaData().getColumnCount();
        } catch (SQLException e) {
            String errorMessage = "Failed to retrieve the column count from the Result Set";
            throw new SQLeonException(e, errorMessage);
        }
    }

    public ResultSetMetaData getMetaData() {
        try {
            this.metaData = resultSet.getMetaData();
        } catch (SQLException e) {
            String errorMessage = "Failed to get meta data from the Result Set.";
            throw new SQLeonException(e, errorMessage);
        }
        return this.metaData;
    }

    public String[] getColumnNames() {
        int columnCount = getColumnCount();
        String[] columnNames = new String[columnCount];

        for (int i = 1; i <= columnCount; i++) {
            String columnName = getColumnName(i);
            columnNames[i] = columnName;
        }
        return columnNames;
    }

    public String[] getRows(String columnName) {
        int columnCount = getColumnCount();
        String[] columnNames = new String[columnCount];

        while (next()) {
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i] = getValue(columnName);
            }
        }
        return columnNames;
    }

    private Boolean next() {
        try {
            return resultSet.next();
        } catch (SQLException e) {
            String errorMessage = "Failed to get next record of the Result Set.";
            throw new SQLeonException(e, errorMessage);
        }
    }

    public String getValue(int i) {
        try {
            return String.valueOf(resultSet.getObject(i));
        } catch (SQLException e) {
            String errorString = "Failed to get value of column number `%s` at row number `%s`.";
            String errorMessage = String.format(errorString, i, getCurrentRowIndex());
            throw new SQLeonException(e, errorMessage);
        }
    }

    public String getValue(String columnLabel) {
        try {
            return String.valueOf(resultSet.getObject(columnLabel));
        } catch (SQLException e) {
            String errorString = "Failed to get value of column named `%s`";
            String errorMessage = String.format(errorString, columnLabel);
            throw new SQLeonException(e, errorMessage);
        }
    }

    public Integer getCurrentRowIndex() {
        try {
            return resultSet.getRow();
        } catch (SQLException e) {
            throw new SQLeonException(e, "Failed to get current row of the Result Set.");
        }
    }

    public ResultSet getResultSet() {
        return resultSet;
    }
}
