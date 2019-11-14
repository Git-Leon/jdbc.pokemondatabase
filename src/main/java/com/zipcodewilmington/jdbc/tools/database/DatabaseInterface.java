package com.zipcodewilmington.jdbc.tools.database;


import com.mysql.jdbc.Driver;
import com.zipcodewilmington.jdbc.tools.database.connection.ConnectionBuilder;
import com.zipcodewilmington.jdbc.tools.database.connection.ConnectionWrapper;
import com.zipcodewilmington.jdbc.tools.database.connection.StatementExecutor;
import com.zipcodewilmington.jdbc.tools.general.exception.SQLeonError;
import com.zipcodewilmington.jdbc.tools.general.logging.LoggerHandler;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface DatabaseInterface {
    static void registerJDBCDriver() {
        // Attempt to register JDBC Driver
        Driver driver = null;
        try {
            driver = (Driver) Class.forName(Driver.class.getName()).newInstance();
            DriverManager.registerDriver(driver);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e1) {
            throw new SQLeonError(e1);
        }
    }

    default boolean isNull() {
        return getConnectionWrapper().hasDatabase(getName());
    }


    default void drop() {
        String sqlStatement = "DROP DATABASE IF EXISTS %s;";
        getStatementExecutor().execute(sqlStatement, getName());
    }


    default void create() {
        String sqlCreateDatabase = "CREATE DATABASE IF NOT EXISTS %s;";
        getStatementExecutor().execute(sqlCreateDatabase, getName());
    }


    default void use() {
        String sqlStatement = "USE %s;";
        getStatementExecutor().execute(sqlStatement, getName());
    }


    default void disableLogging() {
        LoggerHandler logger = getStatementExecutor().getLogger();
        logger.disablePrinting();
    }


    default void enableLogging() {
        LoggerHandler logger = getStatementExecutor().getLogger();
        logger.enablePrinting();
    }


    default <T> void persist(T entity) {
        Class<?> entityClass = entity.getClass();
        boolean isEntity = entityClass.isAnnotationPresent(Entity.class);
        assert (isEntity);

        EntityTransaction entityTransaction = getEntityManager().getTransaction();
        entityTransaction.begin();

        getEntityManager().persist(entity);

        entityTransaction.commit();
    }

    Connection getConnection();

    String getName();

    ConnectionBuilder getConnectionBuilder();

    EntityManager getEntityManager();

    StatementExecutor getStatementExecutor();

    ConnectionWrapper getConnectionWrapper();

    DatabaseTable getTable(String tableName);
}

