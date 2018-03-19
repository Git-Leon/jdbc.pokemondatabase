package com.zipcodewilmington.jdbc.utils.database.dbseed;

import com.zipcodewilmington.jdbc.utils.database.connection.StatementExecutor;
import com.zipcodewilmington.jdbc.utils.io.SQLScriptExecutor;
import com.zipcodewilmington.jdbc.utils.io.SQLScriptExecutorBuilder;

import java.sql.Connection;

public class LeonDatabaseSeeder {
    private final Connection connection;

    public LeonDatabaseSeeder(Connection connection) {
        this.connection = connection;
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
