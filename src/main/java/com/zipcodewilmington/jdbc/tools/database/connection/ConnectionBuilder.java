package com.zipcodewilmington.jdbc.tools.database.connection;

import com.zipcodewilmington.jdbc.tools.general.exception.SQLeonError;
import com.zipcodewilmington.jdbc.tools.general.functional.ExceptionalConsumer;
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
        return setProperty(dataSource::setDatabaseName, databaseName, "database name");
    }

    public ConnectionBuilder setServerName(String serverName) {
        return setProperty(dataSource::setServerName, serverName, "server name");
    }

    public ConnectionBuilder setPort(int portNumber) {
        return setProperty(dataSource::setPort, portNumber, "port number");
    }

    public ConnectionBuilder setUser(String user) {
        return setProperty(dataSource::setUser, user, "server name");
    }

    public ConnectionBuilder setPassword(String password) {
        return setProperty(dataSource::setPassword, password, "password");
    }


    public ConnectionBuilder setUrl(String url) {
        return setProperty(dataSource::setServerName, url, "url");
    }

    private <E> ConnectionBuilder setProperty(ExceptionalConsumer<E> setMethod, E valueToSetTo, String propertyName) {
        try {
            setMethod.accept(valueToSetTo);
        } catch (Throwable throwable) {
            String error = "Failed to setProperty %s to `%s`";
            String errorMessage = String.format(error, propertyName, valueToSetTo);
            throw new SQLeonError(throwable);
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
