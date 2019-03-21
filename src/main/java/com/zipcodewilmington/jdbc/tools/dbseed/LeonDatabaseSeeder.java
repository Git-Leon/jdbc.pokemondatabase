package com.zipcodewilmington.jdbc.tools.dbseed;

import com.zipcodewilmington.jdbc.tools.io.SQLScriptExecutor;
import com.zipcodewilmington.jdbc.tools.io.SQLScriptExecutorBuilder;

import java.sql.Connection;

public class LeonDatabaseSeeder {
    private final Connection connection;

    public LeonDatabaseSeeder(Connection connection) {
        this.connection = connection;
    }


    public void importFilesFromResourcesDirectory() {
        SQLScriptExecutorBuilder builder = new SQLScriptExecutorBuilder(connection);
        String localDirectory = System.getProperty("user.dir");
        String resourcesDirectory = localDirectory + "/src/main/resources/migrations/uat";
        builder.appendDirectory(resourcesDirectory);
        SQLScriptExecutor scriptExecutor = builder.build();
        scriptExecutor.executeScripts();
    }
}
