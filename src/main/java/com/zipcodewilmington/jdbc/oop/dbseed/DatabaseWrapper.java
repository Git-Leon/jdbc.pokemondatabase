package com.zipcodewilmington.jdbc.oop.dbseed;

import com.zipcodewilmington.jdbc.oop.utils.StatementExecutor;

import java.sql.Connection;

public class DatabaseWrapper {

    private final Connection connection;
    private final StatementExecutor statementExectuor;

    public DatabaseWrapper(Database database) {
        this.connection = database.getConnection();
        this.statementExectuor = database.getStatementExecutor();
    }

    // TODO
    public void ceateTable(String name) {
    }

    // TODO
    public Table getTable(String name) {
        return null;
    }
}
