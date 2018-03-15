package com.zipcodewilmington.jdbc.oop.dbseed;

import com.zipcodewilmington.jdbc.oop.dbseed.leon.LeonDatabaseSeeder;
import com.zipcodewilmington.jdbc.oop.utils.ResultSetHandler;
import com.zipcodewilmington.jdbc.oop.utils.StatementExecutor;
import org.junit.Test;

import java.util.Map;

public class LeonDatabaseSeederTest {
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
