package com.github.git_leon.utils.jdbc.database;

import com.github.git_leon.utils.jdbc.connection.ConnectionBuilder;
import com.github.git_leon.utils.jdbc.dbseed.LeonDatabaseSeeder;
import com.github.git_leon.utils.jdbc.resultset.ResultSetHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class DatabaseTableTest {
    private DatabaseTable table;
    private DatabaseInterface database;

    public DatabaseTableTest() {
        this.database = new DatabaseImpl(
                new ConnectionBuilder()
                        .setUrl("jdbc:mysql://localhost/")
                        .setPort(42000)
                        .setDatabaseName("uat")
                        .setServerName("127.0.0.1")
                        .setUser("root")
                        .setPassword("")
                        .setServerTimezone("UTC"), "UAT");
        database.drop();
        database.create();
        database.use();
    }

    @Before
    public void setup() {
        LeonDatabaseSeeder seeder = new LeonDatabaseSeeder(database.getConnection());
        seeder.importFilesFromResourcesDirectory();
        this.table = database.getTable("pokemons");
    }

    @Test
    public void limitTest() {
        // Given
        Integer expectedNumberOfRows = 500;

        // When
        ResultSetHandler results = table.limit(expectedNumberOfRows);
        Integer actualNumberOfRows = results.toStack().size();

        // Then
        Assert.assertEquals(actualNumberOfRows, expectedNumberOfRows);
    }

    @Test
    public void selectTest() {
        // Given
        int expectedNumberOfFields = 2;
        String firstColumn = "name";
        String secondColumn = "secondary_type";
        String columnNames = firstColumn + ", " + secondColumn;

        // When
        ResultSetHandler results = table.select(columnNames);
        Map<String, String> firstRow = results.toStack().pop();
        String firstColumnVal = firstRow.get(firstColumn);
        String secondColumnVal = firstRow.get(secondColumn);
        int actualNumberOfFields = firstRow.size();

        // Then
        Assert.assertEquals(expectedNumberOfFields, actualNumberOfFields);
        Assert.assertNotNull(firstColumnVal);
        Assert.assertNotNull(secondColumnVal);
    }


    @Test
    public void allTest() {
        // Given
        // When
        ResultSetHandler results = table.all();
        Map<String, String> firstRow = results.toStack().pop();
        int actualNumberOfFields = firstRow.size();
        System.out.println(firstRow);
        System.out.println(actualNumberOfFields);
    }

    @Test
    public void toStringTest() {
        System.out.println(table);
    }
}
