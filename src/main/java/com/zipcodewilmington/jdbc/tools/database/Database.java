package com.zipcodewilmington.jdbc.tools.database;

import com.mysql.jdbc.Driver;
import com.zipcodewilmington.jdbc.tools.database.connection.ConnectionBuilder;
import com.zipcodewilmington.jdbc.tools.database.connection.ConnectionWrapper;
import com.zipcodewilmington.jdbc.tools.database.connection.StatementExecutor;
import com.zipcodewilmington.jdbc.tools.exception.SQLeonException;
import com.zipcodewilmington.jdbc.tools.logging.LoggerHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum Database {
    POKEMON(new ConnectionBuilder()
            .setUrl("jdbc:mysql://localhost/")
            .setDatabaseName("pokemon")
            .setServerName("127.0.0.1")
            .setUser("root")
            .setPassword("")
            .build()),

    DIGIMON(new ConnectionBuilder()
            .setUrl("jdbc:mysql://localhost/")
            .setDatabaseName("digimon")
            .setServerName("127.0.0.1")
            .setUser("root")
            .setPassword("")
            .build()),

    MAGIC(new ConnectionBuilder()
            .setUrl("jdbc:mysql://localhost/")
            .setDatabaseName("magic")
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
        return connectionWrapper.hasDatabase(name());
    }

    public void drop() {
        String sqlStatement = "DROP DATABASE IF EXISTS %s;";
        statementExecutor.execute(sqlStatement, name());
    }

    public void create() {
        String sqlStatement = "CREATE DATABASE IF NOT EXISTS %s;";
        statementExecutor.execute(sqlStatement, name());
    }

    public void use() {
        String sqlStatement = "USE %s;";
        statementExecutor.execute(sqlStatement, name());
    }

    public void disableLogging() {
        LoggerHandler logger = statementExecutor.getLogger();
        logger.disablePrinting();
    }

    public void enableLogging() {
        LoggerHandler logger = statementExecutor.getLogger();
        logger.enablePrinting();
    }

    // Attempt to register JDBC Driver
    private static void registerJDBCDriver() {
        Driver driver = null;
        try {
            driver = new Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            try {
                Class.forName(driver.getClass().getName());
            } catch (ClassNotFoundException e1) {
                e.printStackTrace();
                throw new SQLeonException(e1);
            }
        }
    }

    public DatabaseTable getTable(String tableName) {
        return new DatabaseTable(this, tableName);
    }
}
