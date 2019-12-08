package com.github.git_leon.utils.jdbc.dbseed;

import com.github.git_leon.utils.jdbc.executor.SQLScriptExecutor;
import com.github.git_leon.utils.jdbc.executor.SQLScriptExecutorBuilder;

import java.sql.Connection;

/**
 * Created by leon on 3/13/18.
 * Ensures schemas are initialized; Deprecated by `MigrationsTable`
 */
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
