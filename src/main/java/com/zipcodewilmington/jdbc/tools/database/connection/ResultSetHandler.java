package com.zipcodewilmington.jdbc.tools.database.connection;

import com.zipcodewilmington.jdbc.tools.collections.MapCollection;
import com.zipcodewilmington.jdbc.tools.collections.ProperStack;
import gitleon.utils.functional.ExceptionalFunction;
import gitleon.utils.functional.ExceptionalRunnable;
import gitleon.utils.functional.ExceptionalSupplier;

import java.io.Closeable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;

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

    public ProperStack<Map<String, String>> toStack() {
        return toMapCollection().toStack();
    }

    public MapCollection<String, String> toMapCollection() {
        return ExceptionalSupplier.tryInvoke(() -> {
            ResultSetMetaData md = getMetaData();
            int columns = md.getColumnCount();
            MapCollection<String, String> list = new MapCollection<>();
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
        }, "Failed to create map collection");

    }

    public String getColumnName(int i) {
        String errorString = "Failed to get column name of column number `%s`";
        String errorMessage = String.format(errorString, i);
        ExceptionalFunction<Integer, String> method = getMetaData()::getColumnName;
        return ExceptionalFunction.tryInvoke(method, i, errorMessage);
    }

    public Integer getColumnCount() {
        String errorMessage = "Failed to retrieve the column count from the result set";
        return ExceptionalSupplier.tryInvoke(getMetaData()::getColumnCount, errorMessage);
    }

    public ResultSetMetaData getMetaData() {
        String errorMessage = "Failed to get meta data from the result set.";
        this.metaData = ExceptionalSupplier.tryInvoke(resultSet::getMetaData, errorMessage);
        return this.metaData;
    }

    private Boolean next() {
        String errorMessage = "Failed to get next record of the result set.";
        return ExceptionalSupplier.tryInvoke(resultSet::next, errorMessage);
    }

    public String getValue(int i) {
        String errorString = "Failed to get value of column number `%s` at row number `%s`.";
        String errorMessage = String.format(errorString, i, getCurrentRowIndex());
        ExceptionalFunction<Integer, Object> getObjectMethod = resultSet::getObject;
        return String.valueOf(ExceptionalFunction.tryInvoke(getObjectMethod, i, errorMessage));
    }

    public String getValue(String columnLabel) {
        String errorString = "Failed to get value of column named `%s`";
        String errorMessage = String.format(errorString, columnLabel);
        ExceptionalFunction<String, Object> getObjectMethod = resultSet::getObject;
        return String.valueOf(ExceptionalFunction.tryInvoke(getObjectMethod, columnLabel, errorMessage));
    }

    public Integer getCurrentRowIndex() {
        String errorMessage = "Failed to get current row of the result set.";
        return ExceptionalSupplier.tryInvoke(resultSet::getRow, errorMessage);
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


    public ResultSet getResultSet() {
        return resultSet;
    }

    @Override
    public void close() {
        String errorMessage = "Failed to close connection";
        ExceptionalRunnable.tryInvoke(resultSet::close, errorMessage);
    }

    @Override
    public void finalize() {
        close();
    }

    @Override
    public String toString() {
        return toStack().toString();
    }
}