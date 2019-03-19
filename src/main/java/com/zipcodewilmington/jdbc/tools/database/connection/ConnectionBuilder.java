package com.zipcodewilmington.jdbc.tools.database.connection;


import com.mysql.cj.jdbc.MysqlDataSource;
import com.zipcodewilmington.jdbc.tools.general.functional.ExceptionalConsumer;
import com.zipcodewilmington.jdbc.tools.general.functional.ExceptionalSupplier;

import java.sql.Connection;


/**
 * Created by leon on 3/13/18.
 */
@SuppressWarnings("ALL")
public class ConnectionBuilder {
    enum DatabaseProperty {
        NAME,
        PASSWORD,
        PORT_NUMBER,
        SERVER_NAME,
        SERVER_TIMEZONE,
        USER,
        URL;
    }

    private final MysqlDataSource dataSource;

    public ConnectionBuilder() {
        this(new MysqlDataSource());
    }

    public ConnectionBuilder(MysqlDataSource dataSource) {
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

    public ConnectionBuilder setServerTimezone(String serverTimezone) {
        return setProperty(dataSource::setServerTimezone, serverTimezone, DatabaseProperty.SERVER_TIMEZONE);
    }


    public ConnectionBuilder setUrl(String url) {
        return setProperty(dataSource::setUrl, url, DatabaseProperty.URL);
    }

    private <E> ConnectionBuilder setProperty(ExceptionalConsumer<E> setMethod, E valueToSetTo, DatabaseProperty property) {
        String error = "Failed to set property `%s` to `%s`";
        String errorMessage = String.format(error, property.name(), valueToSetTo);
        ExceptionalConsumer.tryInvoke(setMethod, valueToSetTo, errorMessage);
        return this;
    }

    public Connection build() {
        String errorMessage = "Failed to build connection.";
        return ExceptionalSupplier.tryInvoke(dataSource::getConnection, errorMessage);
    }
}
