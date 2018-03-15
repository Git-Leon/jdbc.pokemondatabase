package com.zipcodewilmington.jdbc.oop.dbseed.leon;

import com.zipcodewilmington.jdbc.oop.utils.ConnectionBuilder;
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
        this(new ConnectionBuilder()
                .setDatabaseName("pokemon")
                .setServerName("127.0.0.1")
                .setUser("root")
                .setPassword("")
                .build());
    }

    public void dropTable() {

    }

    public void importFilesFromResourcesDirectory() {
        SQLScriptExecutorBuilder builder = new SQLScriptExecutorBuilder(connection);
        String localDirectory = System.getProperty("user.dir");
        String resourcesDirectory = localDirectory + "/src/main/resources/migrations/";
        builder.appendDirectory(resourcesDirectory);
        SQLScriptExecutor scriptExecutor = builder.build();
        scriptExecutor.executeScripts();
    }

    public StatementExecutor getStatementExecutor() {
        return new StatementExecutor(connection);
    }

    public DataSource getDataSource() {
        return new DataSource(connection);
    }
}
