package com.github.git_leon.utils.jdbc.database;

import com.github.git_leon.utils.jdbc.connection.ConnectionBuilder;
import com.github.git_leon.utils.jdbc.connection.ConnectionWrapper;
import com.github.git_leon.utils.jdbc.executor.StatementExecutor;

import javax.persistence.EntityManager;
import java.sql.Connection;

/**
 * Created by leon on 3/13/18.
 * decoration of DatabaseInterface; Implementation has been loosely coupled to `DatabaseImpl`
 */
public enum Database implements DatabaseInterface {
    POKEMON(new ConnectionBuilder()
            .setUrl("jdbc:mysql://localhost/")
            .setPort(42000)
            .setDatabaseName("pokemon")
            .setServerName("127.0.0.1")
            .setUser("root")
            .setPassword("")
            .setServerTimezone("UTC")),

    UAT(new ConnectionBuilder()
            .setUrl("jdbc:mysql://localhost/")
            .setPort(42000)
            .setDatabaseName("uat")
            .setServerName("127.0.0.1")
            .setUser("root")
            .setPassword("")
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
