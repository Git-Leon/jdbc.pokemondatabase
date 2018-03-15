package com.zipcodewilmington.jdbc.oop.dbseed;

import com.mysql.jdbc.Driver;
import com.zipcodewilmington.jdbc.oop.utils.ConnectionBuilder;
import com.zipcodewilmington.jdbc.oop.utils.ConnectionWrapper;
import com.zipcodewilmington.jdbc.oop.utils.StatementExecutor;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum Database {
    POKEMON(new ConnectionBuilder()
            .setUrl("jdbc:mysql://localhost/")
            .setDatabaseName("pokemon")
            .setServerName("127.0.0.1")
            .setUser("root")
            .setPassword("")
            .build());


    static { // Attempt to register JDBC Driver
        registerJDBCDriver();
    }

    private final ConnectionWrapper connectionWrapper;
    private final StatementExecutor statementExecutor;

    Database(Connection connection) {
        this.connectionWrapper = new ConnectionWrapper(connection);
        this.statementExecutor = new StatementExecutor(connection);
    }

    public Connection getConnection() {
        return connectionWrapper.getConnection();
    }

    public StatementExecutor getStatementExecutor() {
        return this.statementExecutor;
    }

    public boolean isNull() {
        String databaseName = connectionWrapper.getCatalog();
        return databaseName != null;
    }

    public void drop() {
        if (!isNull()) {
            String sqlStatement = "DROP DATABASE " + name();
            statementExecutor.execute(sqlStatement);
        }
    }

    public void create() {
        if (isNull()) {
            String sqlStatement = "CREATE DATABASE " + name();
            statementExecutor.execute(sqlStatement);
        }
    }


    public void use() {
        if (isNull()) {
            create();
        }
        String sqlStatement = "USE DATABASE " + name();
        statementExecutor.execute(sqlStatement);
    }

    public DatabaseMetaData getMetaData() {
        return connectionWrapper.getMetaData();
    }


    // Attempt to register JDBC Driver
    private static void registerJDBCDriver() {
        Driver driver = null;
        try {
            driver = new Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                Class.forName(driver.getClass().getName());
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }
}
