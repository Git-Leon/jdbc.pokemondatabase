package com.zipcodewilmington.jdbc.oop.dbseed;

import com.zipcodewilmington.jdbc.oop.dbseed.leon.LeonDatabaseSeeder;
import com.zipcodewilmington.jdbc.oop.utils.ResultSetHandler;
import com.zipcodewilmington.jdbc.oop.utils.StatementExecutor;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Generated;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class LeonDatabaseSeederTest {

    @Before
    public void setup() {
        Database.POKEMON.drop();
        Database.POKEMON.create();
        Database.POKEMON.use();
    }

    @Test
    public void test() {
        LeonDatabaseSeeder seeder = new LeonDatabaseSeeder();
        seeder.importFilesFromResourcesDirectory();

        StatementExecutor executor = seeder.getStatementExecutor();
        ResultSetHandler rsh = executor.executeQuery("SELECT * FROM pokemons");
        for (Map<String, String> row : rsh.toStack()) {
            System.out.println(row.toString());
        }
    }
}
