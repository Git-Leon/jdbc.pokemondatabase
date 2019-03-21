package com.zipcodewilmington.jdbc.tools.database;

import com.zipcodewilmington.jdbc.tools.database.connection.ResultExtractor;
import com.zipcodewilmington.jdbc.tools.database.connection.ResultSetHandler;
import com.zipcodewilmington.jdbc.tools.database.connection.StatementExecutor;



/**
 * Created by leon on 3/13/18.
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
     * executes a `SELECT * FROM $this.tableName`
     * @return result set handler populated with respective results
     */// TODO - implement StatementBuilder
    public ResultSetHandler all() {
        return where("true");
    }

    /**
     * executes a `SELECT {Columns} FROM $this.tableName`
     * @param fieldNames names of fields to select from Table
     * @return result set handler populated with respective results
     */// TODO - implement StatementBuilder
    public ResultSetHandler select(String fieldNames) {
        return this.where(fieldNames, "true");
    }

    /**
     * executes a `SELECT * FROM $this.tableName`
     * @param fieldNames names of fields to select from Table
     * @param condition string representation of the clause of a `WHERE` statement
     * @return result set handler populated with respective results
     */// TODO - implement StatementBuilder
    public ResultSetHandler where(String fieldNames, String condition) {
        return selectWhereLimit(fieldNames, condition, Integer.MAX_VALUE);
    }

    /**
     * executes a `SELECT {fieldNames} FROM $this.tableName LIMIT {numberOfRows}`
     * @param fieldNames columns to select
     * @param numberOfRows number of rows to be limit this query result by
     * @return result set handler populated with respective results
     */// TODO - implement StatementBuilder
    public ResultSetHandler limit(String fieldNames, Integer numberOfRows) {
        return selectWhereLimit(fieldNames, "true", numberOfRows);
    }

    /**
     * executes a `SELECT * FROM $this.tableName LIMIT {numberOfRows}`
     * @param numberOfRows number of rows to be limit this query result by
     * @return result set handler populated with respective results
     */// TODO - implement StatementBuilder
    public ResultSetHandler limit(Integer numberOfRows) {
        return limit("*", numberOfRows);
    }

    /**
     * executes a `SELECT {fieldNames} FROM $this.tableName WHERE {condition} LIMIT {numberOfRows};`
     * @param fieldNames columns to select
     * @param condition condition under which to select columns
     * @param numberOfRows number of rows to be limit this query result by
     * @return result set handler populated with respective results
     */// TODO - implement StatementBuilder
    public ResultSetHandler selectWhereLimit(String fieldNames, String condition, Integer numberOfRows) {
        return executor.executeQuery(
                "SELECT %s FROM %s WHERE %s LIMIT %s;",
                fieldNames, tableName, condition, numberOfRows);
    }



    /**
     * executes a `SELECT * FROM $this.tableName`
     * @param condition string representation of the clause of a `WHERE` statement
     * @return result set handler populated with respective results
     */// TODO - implement StatementBuilder
    public ResultSetHandler where(String condition) {
        return where("*", condition);
    }
    

    @Override
    public String toString() {
        return select("*").toStack().toString();
    }

    /**
     * @param id
     * @return
     */
    public <T> T find(Long id, ResultExtractor<T> extractor) {
        String condition = "ID = " + id;
        ResultSetHandler rsh = where(condition);
        return extractor.extract(rsh.getResultSet());
    }
}
