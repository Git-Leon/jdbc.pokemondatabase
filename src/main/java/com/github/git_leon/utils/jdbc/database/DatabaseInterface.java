package com.github.git_leon.utils.jdbc.database;


import com.github.git_leon.utils.jdbc.connection.ConnectionBuilder;
import com.github.git_leon.utils.jdbc.connection.ConnectionWrapper;
import com.mysql.jdbc.Driver;
import com.github.git_leon.utils.jdbc.executor.StatementExecutor;
import com.github.git_leon.utils.jdbc.throwable.SQLError;
import com.github.git_leon.utils.jdbc.logging.LoggerHandler;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.lang.annotation.IncompleteAnnotationException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by leon on 3/13/18.
 * provides a behvavioral contract for implementing classes
 */
public interface DatabaseInterface {
    static void registerJDBCDriver() {
        // Attempt to register JDBC Driver
        try {
            DriverManager.registerDriver(Driver.class.newInstance());
        } catch (InstantiationException | IllegalAccessException | SQLException e1) {
            throw new SQLError(e1);
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
        if(!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IncompleteAnnotationException(Entity.class, entityClass.getName());
        }

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

