package com.zipcodewilmington.jdbc.oop.dbseed;

import com.zipcodewilmington.jdbc.oop.dbseed.wilhem.WilhemDatabaseSeeder;
import com.zipcodewilmington.jdbc.oop.utils.ResultSetHandler;
import com.zipcodewilmington.jdbc.oop.utils.StatementExecutor;
import com.zipcodewilmington.jdbc.oop.utils.logging.LoggerWarehouse;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

public class WilhemDatabaseSeederTest {
    private WilhemDatabaseSeeder wilhemDatabaseSeeder;

    public WilhemDatabaseSeederTest() {
        Connection connection = Database.POKEMON.getConnection();
        this.wilhemDatabaseSeeder = new WilhemDatabaseSeeder(connection);
    }

    @Before
    public void setup() {
        LoggerWarehouse.getLogger(StatementExecutor.class).disablePrinting();

        Database.POKEMON.drop();
        Database.POKEMON.create();
        Database.POKEMON.use();
    }

    @Test
    public void test() {
        wilhemDatabaseSeeder.run();

        StatementExecutor executor = wilhemDatabaseSeeder.getStatementExecutor();
        ResultSetHandler rsh = executor.executeQuery("SELECT * FROM pokemons");
        System.out.println(rsh.toStack().toString());
    }
}
