package com.zipcodewilmington.jdbc.tools.database;

import com.zipcodewilmington.jdbc.tools.database.connection.ConnectionBuilder;
import com.zipcodewilmington.jdbc.tools.database.connection.ConnectionWrapper;
import com.zipcodewilmington.jdbc.tools.database.connection.StatementExecutor;
import com.zipcodewilmington.jdbc.tools.exception.SQLeonException;
import com.zipcodewilmington.jdbc.tools.logging.LoggerHandler;
import org.mariadb.jdbc.Driver;

import javax.persistence.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum Database {
    POKEMON(new ConnectionBuilder()
            .setUrl("jdbc:mariadb://localhost/")
            .setPort(3306)
            .setDatabaseName("pokemon")
            .setServerName("127.0.0.1")
            .setUser("root")
            .setPassword("")
            .build()),


    UAT(new ConnectionBuilder()
            .setUrl("jdbc:mariadb://localhost/")
            .setDatabaseName("uat")
            .setServerName("127.0.0.1")
            .setUser("root")
            .setPassword("")
            .build());

    static { // Attempt to register JDBC Driver
        registerJDBCDriver();
    }

    private final ConnectionWrapper connectionWrapper;
    private final StatementExecutor statementExecutor;
    private final EntityManager entityManager;

    Database(Connection connection) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(name());
        this.entityManager = emf.createEntityManager();
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

    public <T> void persist(T entity) {
        Class<?> entityClass = entity.getClass();
        boolean isEntity = entityClass.isAnnotationPresent(Entity.class);
        assert(isEntity);

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        entityManager.persist(entity);

        entityTransaction.commit();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
