package com.zipcodewilmington.jdbc.oop.model;

import org.mariadb.jdbc.MySQLDataSource;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leon on 3/13/18.
 * This class is responsible for centralizing the Data Source logic
 */
public class DataSource implements Closeable {
    private List<ResultSetHandler> resultSetHandlers = new ArrayList<ResultSetHandler>();
    private final MySQLDataSource dataSource;
    private Connection connection;

    /**
     * @param databaseName
     * @param serverName
     * @param user
     * @param password
     */
    public DataSource(String databaseName, String serverName, String user, String password) {
        MySQLDataSource dataSource = new MySQLDataSource();
        dataSource.setDatabaseName(databaseName);
        dataSource.setServerName(serverName);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        this.dataSource = dataSource;
    }

    /**
     * @return connection to the datasource
     */
    public Connection getConnection() {
        if (connection == null) {
            try {
                this.connection = dataSource.getConnection();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return connection;
    }

    /**
     * @param updateStatement string representative of a SQL update statement
     *                        executes update statement on the respective connection object
     */
    public void executeUpdate(String updateStatement) {
        try {
            Statement statement = getConnection().createStatement();
            statement.executeUpdate(updateStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param queryStatement string representative of a SQL query statement
     *                       executes query statement on the respective connection object
     */
    public ResultSetHandler executeQuery(String queryStatement) {
        ResultSetHandler resultSetHandler = this.query(queryStatement);
        resultSetHandlers.add(resultSetHandler);
        return resultSetHandler;
    }

    /**
     * closes connection object and all resultSetHandlers
     */
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (ResultSetHandler resultSetHandler : resultSetHandlers) {
            resultSetHandler.close();
        }
    }

    /**
     * @param queryStatement string representative of a SQL query statement
     *                       executes query statement on the respective connection object
     * @return wrapper of ResultSet
     */
    private ResultSetHandler query(String queryStatement) {
        ResultSet resultSet = null;
        try {
            Statement statement = getConnection().createStatement();
            resultSet = statement.executeQuery(queryStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResultSetHandler(resultSet);
    }

    @Override // Invoked upon garbage collection
    public void finalize() {
        close();
    }

}