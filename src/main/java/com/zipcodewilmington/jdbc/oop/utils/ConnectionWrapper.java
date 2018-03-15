package com.zipcodewilmington.jdbc.oop.utils;

import com.zipcodewilmington.jdbc.oop.utils.exception.SQLeonException;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class ConnectionWrapper {
    private final Connection connection;

    public ConnectionWrapper(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }


    public DatabaseMetaData getMetaData() {
        try {
            return connection.getMetaData();
        } catch (SQLException e) {
            throw getNullPointer(e);
        }
    }

    public String getCatalog() {
        try {
            return connection.getCatalog();
        } catch (SQLException e) {
            throw getNullPointer(e);
        }
    }

    private SQLeonException getNullPointer(SQLException e) {
        String errorMessage = "Failed to get meta data from connectionWrapper with the configured data source.\n";
        return new SQLeonException(e, errorMessage);
    }
}
