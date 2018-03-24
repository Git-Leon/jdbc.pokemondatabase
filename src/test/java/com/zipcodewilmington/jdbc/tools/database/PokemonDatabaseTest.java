package com.zipcodewilmington.jdbc.tools.database;

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

    @Test
    public void t() {

    }
}
