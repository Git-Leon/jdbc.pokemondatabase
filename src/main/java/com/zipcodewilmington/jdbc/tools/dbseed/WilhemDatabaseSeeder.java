package com.zipcodewilmington.jdbc.tools.dbseed;


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
