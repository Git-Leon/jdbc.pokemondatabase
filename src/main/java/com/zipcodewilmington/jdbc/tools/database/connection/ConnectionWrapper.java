package com.zipcodewilmington.jdbc.tools.database.connection;

import com.zipcodewilmington.jdbc.tools.general.exception.SQLeonError;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;


/**
 * Created by leon on 3/13/18.
 */
public class ConnectionWrapper {
    private final Connection connection;

    public ConnectionWrapper(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isClosed() {
        try {
            return connection.isClosed();
        } catch (SQLException e) {
            throw new SQLeonError(e, "Failed to check if the connection was closed.");
        }
    }

    public DatabaseMetaData getMetaData() {
        try {
            return connection.getMetaData();
        } catch (SQLException e) {
            String errorMessage = "Failed to retrieve metadata from the connection.";
            throw new SQLeonError(e, errorMessage);
        }
    }

    public String getCatalog() {
        try {
            return connection.getCatalog();
        } catch (SQLException e) {
            String errorMessage = "Failed to retrieve catalog from the metadata.";
            throw new SQLeonError(e, errorMessage);
        }
    }

    public ResultSetHandler getCatalogs() {
        try {
            ResultSet rs =  getMetaData().getCatalogs();
            ResultSetHandler rsh = new ResultSetHandler(rs);
            return rsh;
        } catch (SQLException e) {
            String errorMessage = "Failed to retrieve the catalogs from the metadata.";
            throw new SQLeonError(e, errorMessage);
        }
    }

    public String[] getSchemaNames() {
        ResultSetHandler rsh = getCatalogs();
        String schemaColumn = rsh.getColumnName(1);
        String[] schemaNames = rsh.getRows(schemaColumn);
        return schemaNames;
    }


    public Boolean hasDatabase(String name) {
        List<String> schemaNames = Arrays.asList(getSchemaNames());
        return schemaNames.contains(name);
    }
}
