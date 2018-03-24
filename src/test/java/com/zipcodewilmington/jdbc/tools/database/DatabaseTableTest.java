package com.zipcodewilmington.jdbc.tools.database;

import com.zipcodewilmington.jdbc.tools.database.connection.ResultSetHandler;
import com.zipcodewilmington.jdbc.tools.testutils.SeedRefresher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class DatabaseTableTest {
    private DatabaseTable table;

    @Before
    public void setup() {
        SeedRefresher.refreshPokemonDatabase();
        Database givenDatabase = Database.POKEMON;
        String givenTableName = "pokemons";
        DatabaseTable givenTable = givenDatabase.getTable(givenTableName);
        this.table = givenTable;
    }

    @Test
    public void limitTest() {
        // Given
        Integer expectedNumberOfRows = 5;

        // When
        ResultSetHandler results = table.limit(expectedNumberOfRows);
        Integer actualNumberOfRows = results.toStack().size();

        // Then
        Assert.assertEquals(expectedNumberOfRows, actualNumberOfRows);
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
    }

    @Test
    public void toStringTest() {
        System.out.println(table);
    }
}
