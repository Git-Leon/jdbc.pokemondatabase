package com.zipcodewilmington.jdbc.dbseed;

import com.zipcodewilmington.jdbc.utils.database.Database;
import com.zipcodewilmington.jdbc.utils.database.DatabaseTable;
import com.zipcodewilmington.jdbc.utils.database.connection.ResultSetHandler;
import com.zipcodewilmington.jdbc.utils.database.connection.StatementExecutor;
import com.zipcodewilmington.jdbc.utils.database.dbseed.WilhemDatabaseSeeder;
import org.junit.Before;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.util.Arrays;

public class WilhemDatabaseSeederTest {
    @Before
    public void setup() {
        Database.POKEMON.disableLogging();
        Database.POKEMON.create();
        Database.POKEMON.use();
    }

    @Test
    public void test() {
        // Given
        Connection connection = Database.POKEMON.getConnection();
        WilhemDatabaseSeeder wilhemDatabaseSeeder = new WilhemDatabaseSeeder(connection);

        // When
        wilhemDatabaseSeeder.run();

        // Then
        System.out.println(Arrays.toString(Database.POKEMON.getSchemas()));
        DatabaseTable pokemons = Database.POKEMON.getTable("pokemons");
        System.out.println(pokemons.toString());
    }
}
