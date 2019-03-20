package com.zipcodewilmington.jdbc.tools.database;

import com.zipcodewilmington.jdbc.tools.database.connection.ResultSetHandler;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class DatabaseTest {
    private final Database database;

    public DatabaseTest() {
        this.database = Database.UAT;
        database.drop();
        database.create();
        database.use();
        database.disableLogging();
    }

    @Before
    public void setup() {
        try { // import sql-insert-statements
            MigrationsTable table = new MigrationsTable(database.getConnection());
            String localProjectRootDirectory = System.getProperty("user.dir");
            String localResourceDirectory = "/src/main/resources/migrations/uat/";
            table.importFilesFromPath(localProjectRootDirectory + localResourceDirectory);
        } catch (IOException e) {
        }
    }

    @Test
    public void getPokemonsTable() {
        // Then
        DatabaseTable pokemons = database.getTable("pokemons");
        ResultSetHandler rsh = pokemons.select("*");
        System.out.println(rsh);
    }
}
