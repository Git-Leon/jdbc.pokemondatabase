package com.zipcodewilmington.jdbc.tools.testutils;

import com.zipcodewilmington.jdbc.tools.database.Database;
import com.zipcodewilmington.jdbc.tools.database.dbseed.WilhemDatabaseSeeder;

import java.sql.Connection;

public class SeedRefresher {
    public static void refresh() {
        Database.POKEMON.drop();
        Database.POKEMON.create();
        Database.POKEMON.use();
        Database.POKEMON.disableLogging();

        // Given
        Connection connection = Database.POKEMON.getConnection();
        WilhemDatabaseSeeder seeder = new WilhemDatabaseSeeder(connection);
        seeder.run();
    }
}