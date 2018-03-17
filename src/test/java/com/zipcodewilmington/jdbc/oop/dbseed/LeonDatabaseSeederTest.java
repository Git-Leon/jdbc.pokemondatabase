package com.zipcodewilmington.jdbc.oop.dbseed;

import com.zipcodewilmington.jdbc.oop.dbseed.leon.LeonDatabaseSeeder;
import com.zipcodewilmington.jdbc.oop.utils.ResultSetHandler;
import com.zipcodewilmington.jdbc.oop.utils.StatementExecutor;
import com.zipcodewilmington.jdbc.oop.utils.logging.LoggerHandler;
import com.zipcodewilmington.jdbc.oop.utils.logging.LoggerWarehouse;
import org.junit.Before;
import org.junit.Test;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.util.Map;

public class LeonDatabaseSeederTest {

    @Before
    public void setup() {
        LoggerWarehouse.getLogger(StatementExecutor.class).disablePrinting();

        Database.POKEMON.drop();
        Database.POKEMON.create();
        Database.POKEMON.use();
    }

    @Test
    public void test() {
        Connection connection = Database.POKEMON.getConnection();
        LeonDatabaseSeeder seeder = new LeonDatabaseSeeder(connection);
        seeder.importFilesFromResourcesDirectory();

        StatementExecutor executor = seeder.getStatementExecutor();
        ResultSetHandler rsh = executor.executeQuery("SELECT * FROM pokemons");
        System.out.println(rsh.toStack().toString());
    }

}
