package com.zipcodewilmington.jdbc.dbseed;

import com.zipcodewilmington.jdbc.utils.database.Database;
import org.junit.Test;

public class PokemonDatabaseTest {

    @Test
    public void use() {
        Database.POKEMON.use();
    }

    @Test
    public void drop() {
        Database.POKEMON.drop();
    }

    @Test
    public void create() {
        Database.POKEMON.create();
    }
}
