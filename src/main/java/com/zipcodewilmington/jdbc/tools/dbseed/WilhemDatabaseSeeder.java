package com.zipcodewilmington.jdbc.tools.dbseed;


import com.zipcodewilmington.jdbc.tools.executor.MigrationsTable;

import java.sql.Connection;

public class WilhemDatabaseSeeder {
    private final Connection connection;

    public WilhemDatabaseSeeder(Connection connection) {
        this.connection = connection;
    }

    public void run() {
        new MigrationsTable(connection).importFilesFromResources();
    }
}
