package com.zipcodewilmington.jdbc.tools.database;

import com.zipcodewilmington.jdbc.tools.connection.ConnectionBuilder;
import com.zipcodewilmington.jdbc.tools.connection.ConnectionWrapper;
import com.zipcodewilmington.jdbc.tools.executor.StatementExecutor;

import javax.persistence.EntityManager;
import java.sql.Connection;

public enum Database implements DatabaseInterface {
    POKEMON(new ConnectionBuilder()
            .setUrl("jdbc:mysql://localhost/")
            .setPort(3306)
            .setDatabaseName("pokemon")
            .setServerName("127.0.0.1")
            .setUser("root")
            .setPassword("newpass")
            .setServerTimezone("UTC")),

    UAT(new ConnectionBuilder()
            .setUrl("jdbc:mysql://localhost/")
            .setPort(3306)
            .setDatabaseName("uat")
            .setServerName("127.0.0.1")
            .setUser("root")
            .setPassword("newpass")
            .setServerTimezone("UTC"));

    static { // Attempt to register JDBC Driver
        DatabaseInterface.registerJDBCDriver();
    }

    private final DatabaseImpl database;

    Database(ConnectionBuilder connectionBuilder) {
        this.database = new DatabaseImpl(connectionBuilder, name());
    }

    public synchronized Connection getConnection() {
        return database.getConnection();
    }

    @Override
    public String getName() {
        return database.getName();
    }

    @Override
    public ConnectionBuilder getConnectionBuilder() {
        return database.getConnectionBuilder();
    }

    @Override
    public StatementExecutor getStatementExecutor() {
        return database.getStatementExecutor();
    }

    @Override
    public ConnectionWrapper getConnectionWrapper() {
        return database.getConnectionWrapper();
    }

    @Override
    public DatabaseTable getTable(String tableName) {
        return database.getTable(tableName);
    }

    @Override
    public EntityManager getEntityManager() {
        return database.getEntityManager();
    }
}
