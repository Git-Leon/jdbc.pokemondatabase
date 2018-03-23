package com.zipcodewilmington.jdbc.tools.database.connection;

import com.zipcodewilmington.jdbc.tools.database.Database;
import com.zipcodewilmington.jdbc.tools.testutils.SeedRefresher;
import org.junit.Before;
import org.junit.Test;

public class ResultSetHandlerTest {
    @Before
    public void setup() {
        SeedRefresher.refresh();
    }

    @Test
    public void test() {
        Database.POKEMON.getTable("pokemons").all();
    }
}
