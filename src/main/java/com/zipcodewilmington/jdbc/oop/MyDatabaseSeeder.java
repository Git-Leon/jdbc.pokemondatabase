package com.zipcodewilmington.jdbc.oop;

import com.zipcodewilmington.jdbc.oop.model.DataSource;
import com.zipcodewilmington.jdbc.oop.model.DatabaseSeeder;

public class MyDatabaseSeeder extends DatabaseSeeder {
    public MyDatabaseSeeder() {
        super(new DataSource(
                "pokemon", // database name
                "127.0.0.1", // server name
                "root", // user
                "") // password
                .getConnection());
    }

    public void importFilesFromResourcesDirectory() {
        String localDirectory = System.getProperty("user.dir");
        String resourcesDirectory = localDirectory + "/src/main/resources/";
        super.importFilesFromDirectory(resourcesDirectory);
    }
}
