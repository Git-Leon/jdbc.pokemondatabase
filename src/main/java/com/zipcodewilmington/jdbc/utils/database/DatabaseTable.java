package com.zipcodewilmington.jdbc.utils.database;

import com.zipcodewilmington.jdbc.utils.database.connection.ResultSetHandler;
import com.zipcodewilmington.jdbc.utils.database.connection.StatementExecutor;

/**
 * Object representation of a Table in a database
 * Very limited features: `SELECT {Columns}`, and `SELECT ALL {Columns} WHERE {Condition}`
 */
public class DatabaseTable {
    private final String tableName;
    private final StatementExecutor executor;

    public DatabaseTable(Database database, String tableName) {
        this.executor = database.getStatementExecutor();
        this.tableName = tableName;
    }

    /**
     * executes a `SELECT {Columns} FROM $this.tableName`
     * @param fieldNames names of fields to select from Table
     * @return result set handler populated with respective results
     */
    public ResultSetHandler select(String fieldNames) {
        // TODO - implement StatementBuidler
        return executor.executeQuery("SELECT %s FROM %s;", fieldNames, tableName);
    }

    /**
     * executes a `SELECT * FROM $this.tableName`
     * @param condition string representation of the clause of a `WHERE` statement
     * @return result set handler populated with respective results
     */
    public ResultSetHandler where(String fieldNames, String condition) {
        // TODO - implement StatementBuidler
        return executor.executeQuery("SELECT %s FROM %s WHERE %s;", fieldNames, tableName, condition);
    }

    @Override
    public String toString() {
        ResultSetHandler rsh = select("*");
        return null;
    }
}
