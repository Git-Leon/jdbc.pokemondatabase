package com.zipcodewilmington.jdbc.utils.database;

import com.zipcodewilmington.jdbc.utils.database.connection.StatementExecutor;

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
    public DatabaseTable getTable(String name) {
        return null;
    }
}
