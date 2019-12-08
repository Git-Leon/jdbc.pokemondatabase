package com.github.git_leon.utils.jdbc.dbseed;

import com.github.git_leon.utils.jdbc.database.Database;
import com.github.git_leon.utils.jdbc.database.DatabaseInterface;
import com.github.git_leon.utils.jdbc.database.DatabaseTable;
import org.junit.Before;
import org.junit.Test;

public class MigrationsTableTest {
    private final DatabaseInterface database;

    public MigrationsTableTest() {
        this.database = Database.UAT;
    }

    @Before
    public void setup() {
        this.database.drop();
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
