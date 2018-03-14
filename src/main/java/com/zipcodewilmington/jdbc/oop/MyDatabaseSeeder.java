package com.zipcodewilmington.jdbc.oop;

import com.zipcodewilmington.jdbc.oop.model.DataSource;
import com.zipcodewilmington.jdbc.oop.model.SQLScriptExecutor;
import com.zipcodewilmington.jdbc.oop.model.SQLScriptExecutorBuilder;
import com.zipcodewilmington.jdbc.oop.model.StatementExecutor;

import java.sql.Connection;
import java.sql.SQLException;
public class MyDatabaseSeeder {
    private final Connection connection;

    public MyDatabaseSeeder() {
        this.connection = new DataSource(
                "pokemon", // database name
                "127.0.0.1", // server name
                "root", // user
                "") // password
                .getConnection();
    }

    public void importFilesFromResourcesDirectory() {
        String localDirectory = System.getProperty("user.dir");
        String resourcesDirectory = localDirectory + "/src/main/resources/migrations/";
        SQLScriptExecutorBuilder builder = new SQLScriptExecutorBuilder(connection);
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
}
