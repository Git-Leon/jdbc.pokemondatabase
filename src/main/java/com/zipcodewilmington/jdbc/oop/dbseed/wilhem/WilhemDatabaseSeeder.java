package com.zipcodewilmington.jdbc.oop.dbseed.wilhem;


import com.zipcodewilmington.jdbc.oop.utils.StatementExecutor;

import java.io.IOException;
import java.sql.Connection;

public class WilhemDatabaseSeeder {
    private final Connection connection;

    public WilhemDatabaseSeeder(Connection connection) {
        this.connection = connection;
    }

    public boolean run() {
        try {
            importFilesFromResourcesDirectory();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void importFilesFromResourcesDirectory() throws IOException {
        MigrationsTable migrationsTable = new MigrationsTable(connection);
        migrationsTable.importFilesFromResources();
    }


    public StatementExecutor getStatementExecutor() {
        return new StatementExecutor(connection);
    }
}
