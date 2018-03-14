package com.zipcodewilmington.jdbc.oop;

import com.zipcodewilmington.jdbc.oop.model.ResultSetHandler;
import com.zipcodewilmington.jdbc.oop.model.StatementExecutor;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by leon on 3/14/18.
 */
public class MainApplication {
    public static void main(String[] args) {
        MyDatabaseSeeder seeder = new MyDatabaseSeeder();
        seeder.importFilesFromResourcesDirectory();
        StatementExecutor executor = seeder.getStatementExecutor();
        ResultSetHandler rsh = executor.executeQuery("SELECT * FROM pokemons");
        try {
            Map<String, String> row1 = rsh.toStack().pop();
            System.out.println(row1.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
