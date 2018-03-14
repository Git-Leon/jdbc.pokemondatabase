package com.zipcodewilmington.jdbc.oop;

/**
 * Created by leon on 3/14/18.
 */
public class MainApplication {
    public static void main(String[] args) {
        MyDatabaseSeeder seeder = new MyDatabaseSeeder();
        seeder.importFilesFromResourcesDirectory();
    }
}
