package com.zipcodewilmington.jdbc.oop.dbseed.leon;

import com.zipcodewilmington.jdbc.oop.dbseed.Database;
import com.zipcodewilmington.jdbc.oop.utils.StatementExecutor;

import java.sql.Connection;

public class LeonDatabaseSeeder {
    private final Connection connection;

    public LeonDatabaseSeeder(Connection connection) {
        this.connection = connection;
    }

    public LeonDatabaseSeeder() {
        this.connection = Database.POKEMON.getConnection();
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

}
