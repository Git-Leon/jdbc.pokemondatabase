package com.zipcodewilmington.jdbc.tools.database;

import com.mysql.jdbc.Driver;
import com.zipcodewilmington.jdbc.tools.database.connection.ConnectionBuilder;
import com.zipcodewilmington.jdbc.tools.database.connection.ConnectionWrapper;
import com.zipcodewilmington.jdbc.tools.database.connection.StatementExecutor;
import com.zipcodewilmington.jdbc.tools.general.exception.SQLeonError;
import com.zipcodewilmington.jdbc.tools.general.logging.LoggerHandler;

import javax.persistence.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum Database {
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
        registerJDBCDriver();
    }

    private final EntityManager entityManager;
    private final ConnectionBuilder connectionBuilder;
    private final Connection connection;

    Database(ConnectionBuilder connectionBuilder) {
        Connection connection = connectionBuilder.build();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(name());

        this.entityManager = emf.createEntityManager();
        this.connection = connection;
        this.connectionBuilder = connectionBuilder;
    }

    public Connection getConnection() {
        ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
        if (connectionWrapper.isClosed()) {
            return connectionBuilder.build();
        }
        return connection;
    }

    public StatementExecutor getStatementExecutor() {
        return new StatementExecutor(getConnection());
    }

    public ConnectionWrapper getConnectionWrapper() {
        return new ConnectionWrapper(getConnection());
    }

    public boolean isNull() {
        return getConnectionWrapper().hasDatabase(name());
    }

    public void drop() {
        String sqlStatement = "DROP DATABASE IF EXISTS %s;";
        getStatementExecutor().execute(sqlStatement, name());
    }

    public void create() {
        String sqlCreateDatabase = "CREATE DATABASE IF NOT EXISTS %s;";
        getStatementExecutor().execute(sqlCreateDatabase, name());
    }

    public void use() {
        String sqlStatement = "USE %s;";
        getStatementExecutor().execute(sqlStatement, name());
    }

    public void disableLogging() {
        LoggerHandler logger = getStatementExecutor().getLogger();
        logger.disablePrinting();
    }

    public void enableLogging() {
        LoggerHandler logger = getStatementExecutor().getLogger();
        logger.enablePrinting();
    }

    public DatabaseTable getTable(String tableName) {
        return new DatabaseTable(this, tableName);
    }

    public <T> void persist(T entity) {
        Class<?> entityClass = entity.getClass();
        boolean isEntity = entityClass.isAnnotationPresent(Entity.class);
        assert (isEntity);

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        entityManager.persist(entity);

        entityTransaction.commit();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    // Attempt to register JDBC Driver
    private static void registerJDBCDriver() {
        Driver driver = null;
        try {
            driver = (Driver) Class.forName(Driver.class.getName()).newInstance();
            DriverManager.registerDriver(driver);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e1) {
            throw new SQLeonError(e1);
        }
    }
}
