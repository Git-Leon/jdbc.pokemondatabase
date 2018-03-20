package com.zipcodewilmington.jdbc.dbseed;

import com.zipcodewilmington.jdbc.utils.database.Database;
import com.zipcodewilmington.jdbc.utils.database.DatabaseTable;
import com.zipcodewilmington.jdbc.utils.database.connection.ResultSetHandler;
import com.zipcodewilmington.jdbc.utils.database.dbseed.LeonDatabaseSeeder;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

public class LeonDatabaseSeederTest {
    @Before
    public void setup() {
        Database.POKEMON.disableLogging();
        Database.POKEMON.drop();
        Database.POKEMON.create();
        Database.POKEMON.use();
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
        String actual = rsh.toStack().toString();
        System.out.println(actual);
    }

}
