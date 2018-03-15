package com.zipcodewilmington.jdbc.oop.dbseed;

import com.zipcodewilmington.jdbc.oop.dbseed.leon.LeonDatabaseSeeder;
import com.zipcodewilmington.jdbc.oop.dbseed.wilhem.WilhemDatabaseSeeder;
import com.zipcodewilmington.jdbc.oop.utils.ResultSetHandler;
import com.zipcodewilmington.jdbc.oop.utils.StatementExecutor;
import org.junit.Test;

import java.util.Map;

public class WilhemDatabaseSeederTest {
    @Test
    public void test() {
        LeonDatabaseSeeder seeder = new LeonDatabaseSeeder();
        WilhemDatabaseSeeder wilhemDatabaseSeeder = new WilhemDatabaseSeeder(seeder.getDataSource().getConnection());
        wilhemDatabaseSeeder.run();
        StatementExecutor executor = wilhemDatabaseSeeder.getStatementExecutor();
        ResultSetHandler rsh = executor.executeQuery("SELECT * FROM pokemons");
        for (Map<String, String> row : rsh.toStack()) {
            System.out.println(row.toString());
        }
    }
}
