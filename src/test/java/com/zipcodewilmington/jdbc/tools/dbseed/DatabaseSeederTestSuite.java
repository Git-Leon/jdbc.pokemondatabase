package com.zipcodewilmington.jdbc.tools.dbseed;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        WilhemDatabaseSeederTest.class,
        LeonDatabaseSeederTest.class,
})
public class DatabaseSeederTestSuite {
}
