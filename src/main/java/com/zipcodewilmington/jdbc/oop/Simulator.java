package com.zipcodewilmington.jdbc.oop;

import com.zipcodewilmington.jdbc.oop.model.DataSource;
import com.zipcodewilmington.jdbc.oop.model.SQLFileImporter;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by leon on 3/13/18.
 */
public class Simulator {
    private final DataSource dataSource;

    public Simulator() {
        String databaseName = "pokemon";
        String serverName = "127.0.0.1";
        String user = "root";
        String password = "";
        this.dataSource = new DataSource(databaseName, serverName, user, password);
    }

    public void importFiles() {
        Connection connection = dataSource.getConnection();
        SQLFileImporter sqlFileImporter = new SQLFileImporter(connection);
        String resourcesDirectory = "/Users/leon/dev/jdbc/src/main/resources/";
        sqlFileImporter.appendDirectory(resourcesDirectory);

        try {
            sqlFileImporter.importFiles();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
