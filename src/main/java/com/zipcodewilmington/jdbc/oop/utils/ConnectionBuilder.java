package com.zipcodewilmington.jdbc.oop.utils;

import com.zipcodewilmington.jdbc.oop.utils.exception.SQLeonException;
import org.mariadb.jdbc.MySQLDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionBuilder {
    private final MySQLDataSource dataSource;

    public ConnectionBuilder() {
        this(new MySQLDataSource());
    }

    public ConnectionBuilder(MySQLDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ConnectionBuilder setDatabaseName(String databaseName) {
        dataSource.setDatabaseName(databaseName);
        return this;
    }

    public ConnectionBuilder setServerName(String serverName) {
        dataSource.setServerName(serverName);
        return this;
    }

    public ConnectionBuilder setPort(int portNumber) {
        dataSource.setPort(portNumber);
        return this;
    }

    public ConnectionBuilder setUser(String user) {
        dataSource.setUser(user);
        return this;
    }

    public ConnectionBuilder setPassword(String password) {
        dataSource.setPassword(password);
        return this;
    }

    public ConnectionBuilder setUrl(String url) {
        dataSource.setUrl(url);
        return this;
    }

    public Connection build() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            String errorMessage = "Failed to build a connection from the configured data source.";
            throw new SQLeonException(e, errorMessage);
        }
    }
}
