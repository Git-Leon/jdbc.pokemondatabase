package com.zipcodewilmington.jdbc.tools.dbseed;

import com.zipcodewilmington.jdbc.tools.database.Database;
import com.zipcodewilmington.jdbc.tools.database.DatabaseTable;
import com.zipcodewilmington.jdbc.tools.connection.ResultSetHandler;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

public class LeonDatabaseSeederTest {
    private LeonDatabaseSeeder seeder;
    private Database database;

    @Before
    public void setup() {
        this.database = Database.UAT;
        database.drop();
        database.create();
        database.use();
        database.disableLogging();
    }

    @Test
    public void test() {
        // Given
        Connection connection = database.getConnection();
        this.seeder = new LeonDatabaseSeeder(connection);

        // When
        seeder.importFilesFromResourcesDirectory();

        // Then
        DatabaseTable pokemons = database.getTable("pokemons");
        ResultSetHandler rsh  = pokemons.select("*");
        System.out.println(rsh);
    }

}
