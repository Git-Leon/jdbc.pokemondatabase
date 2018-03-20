package com.zipcodewilmington.jdbc.dbseed;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        LeonDatabaseSeederTest.class,
        WilhemDatabaseSeederTest.class,
})
public class DatabaseSeederTestSuite {
}
