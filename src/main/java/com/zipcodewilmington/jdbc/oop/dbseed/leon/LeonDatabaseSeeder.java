package com.zipcodewilmington.jdbc.oop.dbseed.leon;

import com.zipcodewilmington.jdbc.oop.utils.DataSource;
import com.zipcodewilmington.jdbc.oop.utils.StatementExecutor;

import java.sql.Connection;
import java.sql.SQLException;
public class LeonDatabaseSeeder {
    private final Connection connection;

    public LeonDatabaseSeeder(Connection connection) {
        this.connection = connection;
    }
    public LeonDatabaseSeeder() {
        this(new DataSource(
                "pokemon", // database name
                "127.0.0.1", // server name
                "root", // user
                "") // password
                .getConnection());
    }

    public void importFilesFromResourcesDirectory() {
        SQLScriptExecutorBuilder builder = new SQLScriptExecutorBuilder(connection);
        String localDirectory = System.getProperty("user.dir");
        String resourcesDirectory = localDirectory + "/src/main/resources/migrations/";
        builder.appendDirectory(resourcesDirectory);
        SQLScriptExecutor scriptExecutor = builder.build();

        try {
            scriptExecutor.executeScripts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public StatementExecutor getStatementExecutor() {
        return new StatementExecutor(connection);
    }

    public DataSource getDataSource() {
        return new DataSource(connection);
    }
}
