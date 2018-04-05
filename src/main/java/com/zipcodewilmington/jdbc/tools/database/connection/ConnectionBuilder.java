package com.zipcodewilmington.jdbc.tools.database.connection;

import com.zipcodewilmington.jdbc.tools.general.exception.SQLeonError;
import com.zipcodewilmington.jdbc.tools.general.functional.ExceptionalConsumer;
import org.mariadb.jdbc.MySQLDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;


/**
 * Created by leon on 3/13/18.
 */
@SuppressWarnings("ALL")
public class ConnectionBuilder {
    enum DatabaseProperty {
        NAME,
        SERVER_NAME,
        PORT_NUMBER,
        USER,
        PASSWORD,
        URL;
    }

    private final MySQLDataSource dataSource;

    public ConnectionBuilder() {
        this(new MySQLDataSource());
    }

    public ConnectionBuilder(MySQLDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ConnectionBuilder setDatabaseName(String databaseName) {
        return setProperty(dataSource::setDatabaseName, databaseName, DatabaseProperty.NAME);
    }

    public ConnectionBuilder setServerName(String serverName) {
        return setProperty(dataSource::setServerName, serverName, DatabaseProperty.SERVER_NAME);
    }

    public ConnectionBuilder setPort(int portNumber) {
        return setProperty(dataSource::setPort, portNumber, DatabaseProperty.PORT_NUMBER);
    }

    public ConnectionBuilder setUser(String user) {
        return setProperty(dataSource::setUser, user, DatabaseProperty.USER);
    }

    public ConnectionBuilder setPassword(String password) {
        return setProperty(dataSource::setPassword, password, DatabaseProperty.PASSWORD);
    }


    public ConnectionBuilder setUrl(String url) {
        return setProperty(dataSource::setUrl, url, DatabaseProperty.URL);
    }

    private <E> ConnectionBuilder setProperty(ExceptionalConsumer<E> setMethod, E valueToSetTo, DatabaseProperty property) {
        try {
            setMethod.accept(valueToSetTo); // invoke setter with respective value
        } catch (Throwable throwable) {
            String error = "Failed to set property `%s` to `%s`";
            String errorMessage = String.format(error, property.name(), valueToSetTo);
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
