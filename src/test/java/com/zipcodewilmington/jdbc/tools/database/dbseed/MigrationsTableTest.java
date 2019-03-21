package com.zipcodewilmington.jdbc.tools.database.dbseed;

import com.zipcodewilmington.jdbc.tools.database.Database;
import com.zipcodewilmington.jdbc.tools.database.DatabaseTable;
import com.zipcodewilmington.jdbc.tools.database.MigrationsTable;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

public class MigrationsTableTest {
    @Before
    public void setup() {
        Database.POKEMON.create();
        Database.POKEMON.use();
        Database.POKEMON.disableLogging();
    }

    @Test
    public void test() {
        // Given
        Connection connection = Database.POKEMON.getConnection();
        MigrationsTable migrationsTable = new MigrationsTable(connection);

        // When
        migrationsTable.importFilesFromResources();

        // Then
        DatabaseTable pokemons = Database.POKEMON.getTable("pokemons");
        System.out.println(pokemons.toString());
    }
}
