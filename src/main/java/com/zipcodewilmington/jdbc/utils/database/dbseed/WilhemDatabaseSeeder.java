package com.zipcodewilmington.jdbc.utils.database.dbseed;


import com.zipcodewilmington.jdbc.utils.database.MigrationsTable;
import com.zipcodewilmington.jdbc.utils.database.connection.StatementExecutor;

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
