package com.zipcodewilmington.jdbc.tools.dbseed;

import com.zipcodewilmington.jdbc.tools.database.Database;
import com.zipcodewilmington.jdbc.tools.database.DatabaseInterface;
import com.zipcodewilmington.jdbc.tools.database.DatabaseTable;
import org.junit.Before;
import org.junit.Test;

public class MigrationsTableTest {
    private final DatabaseInterface database;

    public MigrationsTableTest() {
        this.database = Database.POKEMON;
    }

    @Before
    public void setup() {
        this.database.create();
        this.database.use();
        this.database.disableLogging();
    }

    @Test
    public void test() {
        // Given
        MigrationsTable seeder = new MigrationsTable(database.getConnection());

        // When
        seeder.importFilesFromResources();

        // Then
        DatabaseTable pokemons = database.getTable("pokemons");
        System.out.println(pokemons.toString());
    }
}
