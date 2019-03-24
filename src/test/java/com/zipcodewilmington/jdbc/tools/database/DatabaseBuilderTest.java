package com.zipcodewilmington.jdbc.tools.database;

import com.zipcodewilmington.jdbc.mvc.entity.Pokemon;
import org.junit.Test;

public class DatabaseBuilderTest {
    @Test
    public void test1() {
        DatabaseTableBuilder databaseTableBuilder = new DatabaseTableBuilder(Database.UAT);
        String createStatement = databaseTableBuilder.getCreateStatement(Pokemon.class);
        System.out.println(createStatement);
    }
}
