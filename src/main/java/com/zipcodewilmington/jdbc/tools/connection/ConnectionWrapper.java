package com.zipcodewilmington.jdbc.tools.connection;

import com.zipcodewilmington.jdbc.tools.resultset.ResultSetHandler;
import gitleon.utils.exceptionalfunctionalinterface.ExceptionalConsumer;
import gitleon.utils.exceptionalfunctionalinterface.ExceptionalSupplier;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;


/**
 * Created by leon on 3/13/18.
 */
public class ConnectionWrapper {
    enum ConnectionProperty {
        IS_CLOSED,
        METADATA,
        CATALOG,
        CATALOGS, AUTOCOMMIT;
    }

    private final Connection connection;

    public ConnectionWrapper(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isClosed() {
        return getProperty(connection::isClosed, ConnectionProperty.IS_CLOSED);
    }

    public DatabaseMetaData getMetaData() {
        return getProperty(connection::getMetaData, ConnectionProperty.METADATA);
    }

    public String getCatalog() {
        return getProperty(connection::getCatalog, ConnectionProperty.CATALOG);
    }

    public ResultSetHandler getCatalogs() {
        return getProperty(() -> {
            ResultSet rs = getMetaData().getCatalogs();
            ResultSetHandler rsh = new ResultSetHandler(rs);
            return rsh;
        }, ConnectionProperty.CATALOGS);
    }

    public void setAutoCommit(Boolean flag) {
        setProperty(connection::setAutoCommit, flag, ConnectionProperty.AUTOCOMMIT);
    }

    public String[] getSchemaNames() {
        ResultSetHandler rsh = getCatalogs();
        String schemaColumn = rsh.getColumnName(1);
        String[] schemaNames = rsh.getRows(schemaColumn);
        return schemaNames;
    }

    public Boolean hasDatabase(String name) {
        return Arrays.asList(getSchemaNames()).contains(name);
    }


    private <E> void setProperty(ExceptionalConsumer<E> setMethod, E argument, ConnectionProperty property) {
        String error = "Failed to set property `%s`";
        String errorMessage = String.format(error, property.name());
        ExceptionalConsumer.tryInvoke(setMethod::accept, argument, errorMessage);
    }

    private <E> E getProperty(ExceptionalSupplier<E> getMethod, ConnectionProperty property) {
        String error = "Failed to get property `%s`";
        String errorMessage = String.format(error, property.name());
        return ExceptionalSupplier.tryInvoke(getMethod::get, errorMessage);
    }
}
