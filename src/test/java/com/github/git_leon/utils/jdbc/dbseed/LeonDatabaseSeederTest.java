package com.github.git_leon.utils.jdbc.dbseed;

import com.github.git_leon.utils.jdbc.database.Database;
import com.github.git_leon.utils.jdbc.database.DatabaseTable;
import com.github.git_leon.utils.jdbc.resultset.ResultSetHandler;
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
