package com.zipcodewilmington.jdbc.tools.database;

import com.zipcodewilmington.jdbc.tools.database.connection.ConnectionWrapper;
import com.zipcodewilmington.jdbc.tools.database.connection.StatementExecutor;

import javax.persistence.EntityManager;
import java.sql.Connection;

public interface DatabaseInterface {
    boolean isNull();

    void create();

    void drop();

    void use();

    void disableLogging();

    void enableLogging();

    <T> void persist(T entity);

    Connection getConnection();

    ConnectionWrapper getConnectionWrapper();

    EntityManager getEntityManager();

    StatementExecutor getStatementExecutor();

    DatabaseTable getTable(String tableName);
}
