package com.zipcodewilmington.jdbc.oop.utils;


import com.zipcodewilmington.jdbc.oop.utils.exception.SQLeonException;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/**
 * @author leon.hunter
 * @purpose Centralized logic for handling instances of ResultSet
 */
@SuppressWarnings("ALL")
public class ResultSetManager implements AutoCloseable {
    private final ResultSet rs;
    private final ResultSetMetaData md;
    private List<Map<String, Object>> hashMapList;
    private Stack<Map<String, Object>> hashMapStack;

    public ResultSetManager(ResultSet rs) {
        this.rs = rs;
        this.md = getMetaData();
    }

    // Returns a { columnName : columnValues } structured hash map.
    public Map<String, String[]> asColumnNameMap() {
        Map<String, String[]> table = new HashMap<String, String[]>() {
            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                Set<Entry<String, String[]>> entrySet = entrySet();
                for (Entry<String, String[]> stringEntry : entrySet) {
                    String columnName = stringEntry.getKey();
                    String[] columnValues = stringEntry.getValue();
                    String columnVals = Arrays.toString(columnValues);

                    sb.append("\n" + columnName + " = " + columnVals);
                }
                return sb.toString();
            }
        };

        int columnCount = 0;
        try {
            columnCount = md.getColumnCount();
            int rowCount = getRowCount();
            for (String field : getColumnNames()) { // new String[] per column
                table.put(field, new String[rowCount]);
            }
            for (int j = 0; rs.next(); j++) { // every row
                for (int i = 1; i < columnCount; ++i) { // every column
                    table.get(md.getColumnName(i))[j] = "" + rs.getObject(i);
                }
            }
        } catch (SQLException e) {
            throw new SQLeonException(e, "Failed to create column name map");
        }
        return table;
    }

    // Returns stack of hash maps structured as { columnName : columnValue }
    public Stack<Map<String, Object>> asMapStack() {
        if (this.hashMapStack == null) {
            hashMapStack = new Stack<Map<String, Object>>();
            for (Map<String, Object> row : asMapList()) {
                hashMapStack.push(row);
            }
        }
        return hashMapStack;
    }

    // Returns list of { columnName : columnValue } structured hash maps
    public List<Map<String, Object>> asMapList() {
        if (this.hashMapList == null) {
            hashMapList = new ArrayList<Map<String, Object>>();
            try {

                int columnCount = md.getColumnCount();
                while (rs.next()) { // iterate over rows
                    HashMap<String, Object> row = new HashMap<String, Object>(columnCount);
                    for (int i = 1; i <= columnCount; ++i) { // iterate over columns
                        row.put(md.getColumnName(i), rs.getObject(i));
                    }
                    hashMapList.add(row);
                }
                rs.beforeFirst();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
        return hashMapList;
    }

    public ArrayList<String> getColumnNames() {
        try {
            ArrayList<String> columnNames = new ArrayList<String>();
            int columnCount = md.getColumnCount();
            for (int i = 1; i < columnCount; i++) {
                columnNames.add(md.getColumnName(i));
            }
            return columnNames;
        } catch (SQLException e) {
            e.printStackTrace();

            return null;
        }
    }

    public int getRowCount() {
        int count = 0;
        try {
            rs.last();
            count = rs.getRow();
            rs.beforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();

            count = -1;
        }
        return count;
    }

    @Override
    public void close() {
        try {
            this.rs.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    // used for initialization
    private ResultSetMetaData getMetaData() {
        ResultSetMetaData rsmd = null;
        try {
            rsmd = getResultSet().getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return rsmd;
    }

    public ResultSet getResultSet() {
        return this.rs;
    }
}
