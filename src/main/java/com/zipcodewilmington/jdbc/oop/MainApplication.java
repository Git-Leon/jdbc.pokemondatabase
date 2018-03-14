package com.zipcodewilmington.jdbc.oop;

import com.zipcodewilmington.jdbc.Migrations.MyDatabaseSeeder;
import com.zipcodewilmington.jdbc.oop.model.DataSource;
import org.mariadb.jdbc.MySQLDataSource;

/**
 * Created by leon on 3/14/18.
 */
public class MainApplication {
    public static void main(String[] args) {
        DataSource ds = new DataSource("pokemon", "localhost", "root", "");

        MyDatabaseSeeder seeder = new MyDatabaseSeeder(ds);
        seeder.run();
    }
}
