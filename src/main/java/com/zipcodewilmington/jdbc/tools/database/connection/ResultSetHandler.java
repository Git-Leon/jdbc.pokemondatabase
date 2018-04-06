package com.zipcodewilmington.jdbc.tools.database.connection;

import com.zipcodewilmington.jdbc.tools.collections.MapCollection;
import com.zipcodewilmington.jdbc.tools.collections.ProperStack;
import com.zipcodewilmington.jdbc.tools.general.exception.SQLeonError;
import com.zipcodewilmington.jdbc.tools.general.functional.ExceptionalConsumer;
import com.zipcodewilmington.jdbc.tools.general.functional.ExceptionalFunction;
import com.zipcodewilmington.jdbc.tools.general.functional.ExceptionalRunnable;
import com.zipcodewilmington.jdbc.tools.general.functional.ExceptionalSupplier;

import java.io.Closeable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

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
        return tryInvoke(() -> {
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

    @Override
    public void close() {
        String errorMessage = "Failed to close connection";
        tryInvoke(resultSet::close, errorMessage);
    }

    @Override
    public void finalize() {
        close();
    }

    public String getColumnName(int i) {
        String errorString = "Failed to get column name of column number `%s`";
        String errorMessage = String.format(errorString, i);
        return tryInvoke(getMetaData()::getColumnName, i, errorMessage);
    }

    public Integer getColumnCount() {
        String errorMessage = "Failed to retrieve the column count from the result set";
        return tryInvoke(getMetaData()::getColumnCount, errorMessage);
    }

    public ResultSetMetaData getMetaData() {
        String errorMessage = "Failed to get meta data from the result set.";
        this.metaData = tryInvoke(resultSet::getMetaData, errorMessage);
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
        String errorMessage = "Failed to get next record of the result set.";
        return tryInvoke(resultSet::next, errorMessage);
    }

    public String getValue(int i) {
        String errorString = "Failed to get value of column number `%s` at row number `%s`.";
        String errorMessage = String.format(errorString, i, getCurrentRowIndex());
        ExceptionalFunction<Integer, Object> ef = resultSet::getObject;
        return String.valueOf(tryInvoke(ef, i, errorMessage));
    }

    public String getValue(String columnLabel) {
        String errorString = "Failed to get value of column named `%s`";
        String errorMessage = String.format(errorString, columnLabel);
        ExceptionalFunction<String, Object> ef = resultSet::getObject;
        return String.valueOf(tryInvoke(ef, columnLabel, errorMessage));
    }

    public Integer getCurrentRowIndex() {
        String errorMessage = "Failed to get current row of the result set.";
        return tryInvoke(resultSet::getRow, errorMessage);
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    @Override
    public String toString() {
        return toStack().toString();
    }


    private void tryInvoke(ExceptionalRunnable method, String errorMessage) {
        tryInvoke(method, errorMessage);
    }

    private <ReturnType> ReturnType tryInvoke(ExceptionalSupplier<ReturnType> method, String errorMessage) {
        ReturnType valueRetrieved = null;
        try {
            valueRetrieved = method.get(); // invoke method
        } catch (Throwable throwable) {
            throw new SQLeonError(throwable, errorMessage);
        }
        return valueRetrieved;
    }

    private <ArgType, ReturnType> ReturnType tryInvoke(ExceptionalFunction<ArgType, ReturnType> method, ArgType argType, String errorMessage) {
        ReturnType valueRetrieved = null;
        try {
            valueRetrieved = method.apply(argType); // invoke method
        } catch (Throwable throwable) {
            throw new SQLeonError(throwable, errorMessage);
        }
        return valueRetrieved;
    }
}