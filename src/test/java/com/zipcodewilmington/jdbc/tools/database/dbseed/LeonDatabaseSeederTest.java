package com.zipcodewilmington.jdbc.tools.database.dbseed;

import com.zipcodewilmington.jdbc.tools.database.Database;
import com.zipcodewilmington.jdbc.tools.database.DatabaseTable;
import com.zipcodewilmington.jdbc.tools.database.connection.ResultSetHandler;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

public class LeonDatabaseSeederTest {
    @Before
    public void setup() {
        Database.POKEMON.drop();
        Database.POKEMON.create();
        Database.POKEMON.use();
        Database.POKEMON.disableLogging();
    }

    @Test
    public void test() {
        // Given
        Connection connection = Database.POKEMON.getConnection();
        LeonDatabaseSeeder seeder = new LeonDatabaseSeeder(connection);

        // When
        seeder.importFilesFromResourcesDirectory();

        // Then
        DatabaseTable pokemons = Database.POKEMON.getTable("pokemons");
        ResultSetHandler rsh  = pokemons.select("*");
        System.out.println(rsh);
    }

}
