package com.zipcodewilmington.jdbc.dbseed;

import com.zipcodewilmington.jdbc.utils.database.Database;
import com.zipcodewilmington.jdbc.utils.database.connection.ResultSetHandler;
import com.zipcodewilmington.jdbc.utils.database.connection.StatementExecutor;
import com.zipcodewilmington.jdbc.utils.database.dbseed.WilhemDatabaseSeeder;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

public class WilhemDatabaseSeederTest {
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
        WilhemDatabaseSeeder wilhemDatabaseSeeder = new WilhemDatabaseSeeder(connection);

        // When
        wilhemDatabaseSeeder.run();

        // Then
        StatementExecutor executor = wilhemDatabaseSeeder.getStatementExecutor();
        ResultSetHandler rsh = executor.executeQuery("SELECT * FROM pokemons");
        System.out.println(rsh.toStack().toString());
    }
}
