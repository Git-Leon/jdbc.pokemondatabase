package com.zipcodewilmington.jdbc.tools.database.connection;

import com.zipcodewilmington.jdbc.tools.exception.SQLeonError;
import org.mariadb.jdbc.MySQLDataSource;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * Created by leon on 3/13/18.
 */
@SuppressWarnings("ALL")
public class ConnectionBuilder {
    private final MySQLDataSource dataSource;

    public ConnectionBuilder() {
        this(new MySQLDataSource());
    }

    public ConnectionBuilder(MySQLDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ConnectionBuilder setDatabaseName(String databaseName) {
        try {
            dataSource.setDatabaseName(databaseName);
        } catch (SQLException e) {
            String errorMessage = "Failed to set database name to";
            throw new SQLeonError(e, errorMessage);
        }
        return this;
    }

    public ConnectionBuilder setServerName(String serverName) {
        try {
            dataSource.setServerName(serverName);
        } catch (SQLException e) {
            // TODO - add error message
            throw new SQLeonError(e);
        }
        return this;
    }

    public ConnectionBuilder setPort(int portNumber) {
        try {
            dataSource.setPort(portNumber);
        } catch (SQLException e) {
            // TODO - add error message
            throw new SQLeonError(e);
        }
        return this;
    }

    public ConnectionBuilder setUser(String user) {
        try {
            dataSource.setUser(user);
        } catch (SQLException e) {
            // TODO - add error message
            throw new SQLeonError(e);
        }
        return this;
    }

    public ConnectionBuilder setPassword(String password) {
        try {
            dataSource.setPassword(password);
        } catch (SQLException e) {
            // TODO - add error message
            throw new SQLeonError(e);
        }
        return this;
    }

    public ConnectionBuilder setUrl(String url) {
        try {
            dataSource.setUrl(url);
        } catch (SQLException e) {
            // TODO - add error message
            throw new SQLeonError(e);
        }
        return this;
    }

    public Connection build() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            String errorMessage = "Failed to build a connection from the configured data source.";
            throw new SQLeonError(e, errorMessage);
        }
    }
}
